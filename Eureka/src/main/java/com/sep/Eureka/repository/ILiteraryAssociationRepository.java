package com.sep.Eureka.repository;

import com.sep.Eureka.entity.LiteraryAssociation;
import com.sep.Eureka.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ILiteraryAssociationRepository extends JpaRepository<LiteraryAssociation, UUID> {

    Set<LiteraryAssociation> findAllByPaymentType(PaymentType paymentType);

    LiteraryAssociation findByLuId(String lu_id);

}
