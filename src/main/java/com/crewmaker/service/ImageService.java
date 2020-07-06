package com.crewmaker.service;

import com.crewmaker.entity.EventPlace;
import com.crewmaker.entity.EventPlaceImage;
import com.crewmaker.entity.User;
import com.crewmaker.entity.UserProfileImage;
import com.crewmaker.exception.AppException;
import com.crewmaker.exception.ResourceNotFoundException;
import com.crewmaker.repository.EventPlaceImageRepository;
import com.crewmaker.repository.EventPlaceRepository;
import com.crewmaker.repository.UserProfileImageRepository;
import com.crewmaker.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileImageRepository userProfileImageRepository;

    @Autowired
    EventPlaceRepository eventPlaceRepository;

    @Autowired
    EventPlaceImageRepository eventPlaceImageRepository;

    public byte[] getEventPlaceImage(Long eventPlaceID, int height , int width){
        EventPlace eventPlace = Optional.ofNullable(eventPlaceRepository.findByEventPlaceId(eventPlaceID))
                .orElseThrow(() -> new ResourceNotFoundException("EventPlace", "eventPlace", eventPlaceID));

        EventPlaceImage retrivedImage = Optional.ofNullable(eventPlace.getPhotoLink())
                .orElseThrow(() -> new ResourceNotFoundException("EventPlaceImage", "photolink", null));

        return scale(decompressBytes(retrivedImage.getBinaryData()),height,width);
    }

    public byte[] getUserProfileImage(String username, boolean isSmall){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfileImage retrivedImage = Optional.ofNullable(user.getUserProfileImage())
                .orElseThrow(() -> new ResourceNotFoundException("UserProfileImage", "userProfileImage", null));
        int size = isSmall ? 100 : 256;
        return scale(decompressBytes(retrivedImage.getBinaryData()),size,size);
    }

    public boolean uploadEventPlaceImage(MultipartFile file, Long eventPlaceID) throws IOException {
        try{
            EventPlace eventPlace = Optional.ofNullable(eventPlaceRepository.findByEventPlaceId(eventPlaceID))
                    .orElseThrow(() -> new ResourceNotFoundException("EventPlace", "eventPlace", eventPlaceID));

            Optional<EventPlaceImage> currentImg = Optional.ofNullable(eventPlace.getPhotoLink());
            if(currentImg.isPresent()){
                eventPlaceImageRepository.setEventPlaceImageById(file.getOriginalFilename(), file.getContentType(),
                        compressBytes(file.getBytes()), currentImg.get().getEventPlaceImageID());
            }else{
                eventPlace.setPhotoLink(new EventPlaceImage(file.getOriginalFilename(), file.getContentType(),
                        compressBytes(file.getBytes())));
                eventPlaceRepository.save(eventPlace);
            }
        }catch(ResourceNotFoundException e){
            return false;
        }
        return true;
    }

    public boolean uploadUserImage(MultipartFile file) throws IOException {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

            Optional<UserProfileImage> currentImg = Optional.ofNullable(user.getUserProfileImage());
            if(currentImg.isPresent()){
                userProfileImageRepository.setUserProfileImageById(file.getOriginalFilename(), file.getContentType(),
                        compressBytes(file.getBytes()), currentImg.get().getImageId());
            }else{
                user.setUserProfileImage(new UserProfileImage(file.getOriginalFilename(), file.getContentType(),
                        compressBytes(file.getBytes())));
                userRepository.save(user);
            }
        }catch(ResourceNotFoundException e){
            return false;
        }
        return true;
    }

    public byte[] scale(byte[] fileData, int height, int width) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(imageBuff, "png", buffer);
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new AppException("IOException in scale");
        }
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}