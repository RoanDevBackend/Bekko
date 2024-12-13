package com.example.bookingserver.application.command.handle.patient;

import com.example.bookingserver.application.command.command.patient.EmergencyContactCommand;
import com.example.bookingserver.application.command.handle.exception.BookingCareException;
import com.example.bookingserver.application.command.handle.exception.ErrorDetail;
import com.example.bookingserver.domain.EmergencyContact;
import com.example.bookingserver.domain.Patient;
import com.example.bookingserver.infrastructure.constant.ApplicationConstant;
import com.example.bookingserver.infrastructure.mapper.PatientMapper;
import com.example.bookingserver.infrastructure.message.MessageProducer;
import com.example.bookingserver.infrastructure.persistence.repository.EmergencyContactRepository;
import com.example.bookingserver.infrastructure.persistence.repository.PatientRepository;
import document.constant.TopicConstant;
import document.event.patient.EmergencyContactEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewEmergencyContactHandler {
    private final EmergencyContactRepository emergencyContactRepository;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final MessageProducer  messageProducer;
    final String TOPIC= TopicConstant.PatientTopic.CREATE_CONTACT;

    @SneakyThrows
    public void execute(String patientId, EmergencyContactCommand command){
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new BookingCareException(ErrorDetail.ERR_USER_NOT_EXISTED)
        );
        EmergencyContact emergencyContact= patientMapper.toEmergencyContact(command);
        emergencyContact.setPatient(patient);
        emergencyContactRepository.save(emergencyContact);

        EmergencyContactEvent event= patientMapper.toEmergencyContactEvent(emergencyContact);
        messageProducer.sendMessage(TOPIC, ApplicationConstant.EventType.ADD, event, event.getId()+"", "");
    }
}
