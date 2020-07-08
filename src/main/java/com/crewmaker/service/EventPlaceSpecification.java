package com.crewmaker.service;

import com.crewmaker.entity.EventPlace;
import com.crewmaker.entity.metamodel.EventPlace_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EventPlaceSpecification {

    public static Specification<EventPlace> getEventPlaceByAccepted(Boolean isAccepted, Boolean isArchived, String city) {
        return new Specification<EventPlace>() {
            @Override
            public Predicate toPredicate(Root<EventPlace> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                final List<Predicate> predicates = new ArrayList<>();

                if (Optional.ofNullable(isAccepted).isPresent()) {
                    predicates.add(criteriaBuilder.equal(root.get(EventPlace_.IS_ACCEPTED), isAccepted));
                }
                if (Optional.ofNullable(isArchived).isPresent()) {
                    predicates.add(criteriaBuilder.equal(root.get(EventPlace_.IS_ARCHIVED), isArchived));
                }
                if (Optional.ofNullable(city).isPresent()) {
                    predicates.add(criteriaBuilder.like(root.get(EventPlace_.CITY), "%" + city + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}