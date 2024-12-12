package kata.paiementservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import kata.shareddto.paimentservice.PaiementResponseDTO;
import org.apache.kafka.common.serialization.Serializer;

public class PaiementResponseDTOSerializer implements Serializer<PaiementResponseDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, PaiementResponseDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de sérialisation de PaiementResponseDTO", e);
        }
    }
}
