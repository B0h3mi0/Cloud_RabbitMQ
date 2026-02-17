package com.transp_national.consumer_queue_2.infrastructure.adapters.out.database.s3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transp_national.consumer_queue_2.application.ports.out.SaveScheduleFilePort;
import com.transp_national.consumer_queue_2.domain.model.Schedule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@Primary
public class S3ScheduleAdapter implements SaveScheduleFilePort {

    private final S3Client s3Client;
    private final ObjectMapper objectMapper;
    private final String bucketName;

    public S3ScheduleAdapter(
            @Value("${aws.s3.bucket.name}") String bucketName) {
        this.bucketName = bucketName;
        this.objectMapper = new ObjectMapper();

        // AWS SDK v2 tomará automáticamente las credenciales de las variables de entorno
        this.s3Client = S3Client.builder().build();
    }

    @Override
    public void saveToFile(Schedule schedule) {
        try {
            // 1. Generar el nombre del archivo
            String fileName = "schedules/schedule_" + schedule.routeId() + "_" + System.currentTimeMillis() + ".json";

            // 2. Convertir el objeto a un String JSON
            String jsonContent = objectMapper.writeValueAsString(schedule);

            // 3. Preparar la petición para S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType("application/json")
                    .build();

            // 4. Subir el archivo
            s3Client.putObject(putObjectRequest, RequestBody.fromString(jsonContent));

            System.out.println("✅ Archivo JSON subido exitosamente a S3: s3://" + bucketName + "/" + fileName);

        } catch (Exception e) {
            System.err.println("❌ Error al subir el archivo a S3: " + e.getMessage());
        }
    }
}