package kata.commandeservice.configuration;



import com.fasterxml.jackson.databind.ObjectMapper;

import kata.shareddto.paimentservice.PaiementRequestDTO;
import org.apache.kafka.common.serialization.Serializer;

public class PaiementRequestDTOSerializer implements Serializer<PaiementRequestDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, PaiementRequestDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("erreur de serialisation de PaiementRequestDTO", e);
        }
    }
}

