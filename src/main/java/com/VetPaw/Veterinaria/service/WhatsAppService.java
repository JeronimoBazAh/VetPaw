package com.VetPaw.Veterinaria.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    @Value("${twilio.whatsapp.enabled:true}")
    private boolean whatsappEnabled;

    @PostConstruct
    public void init(){
        if(whatsappEnabled){
            Twilio.init(accountSid,authToken);
            log.info("Twilio WhatsApp inicializado correctamente");
        }else{
            log.warn("WhatsApp deshabilitado en configuracion");
        }
    }

    public boolean enviarMensaje(String destinatario, String mensaje){

        if(!whatsappEnabled){
            log.warn("Whatspp deshabilitado. No se envio el mensaje");
            return false;
        }
        try{
            String numeroDestino = formatearNumeroWhatsapp(destinatario);

            Message message = Message.creator(
                    new PhoneNumber(numeroDestino),
                    new PhoneNumber(fromNumber),
                    mensaje
            ).create();

            log.info("Whatsapp enviado a {} - SID: {}", destinatario, message.getSid());
            return true;
        } catch (Exception e){
            log.error("Error enviando whatsapp");
            return false;
        }



    }

    private String formatearNumeroWhatsapp(String numero){

        numero = numero.replaceAll("[^0-9+]", "");

        if(!numero.startsWith("+")){
            numero = "+549" + numero;
        }
        return "whatsapp" + numero;
    }
    public boolean enviarAlertaTratamiento(String celular, String nombrePropietario,
                                          String nombreMascota,String tratamiento, long horasAtraso) {

        String emoji;
        String nivel;

        if (horasAtraso >= 24) {
            emoji = "🚨";
            nivel = "CRÍTICO";
        } else if (horasAtraso >= 12) {
            emoji = "⚠️";
            nivel = "URGENTE";
        } else if (horasAtraso >= 4) {
            emoji = "⏰";
            nivel = "IMPORTANTE";
        } else {
            emoji = "📢";
            nivel = "RECORDATORIO";
        }

        String mensaje = String.format(
                "%s *ALERTA %s - VetPaw*\n\n" +
                        "Hola *%s*,\n\n" +
                        "Tu mascota *%s* tiene un atraso de *%d hora(s)* en el tratamiento:\n\n" +
                        "🔹 Tratamiento: %s\n" +
                        "🔹 Atraso: %d hora(s)\n\n" +
                        "Por favor, aplica la dosis lo antes posible.\n\n" +
                        "Si ya lo hiciste, puedes confirmarlo en la app.\n\n" +
                        "_Mensaje automático - VetPaw_",
                emoji, nivel, nombrePropietario, nombreMascota, horasAtraso, tratamiento, horasAtraso
        );
        return enviarMensaje(celular, mensaje);
    }




}
