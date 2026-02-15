package com.transp_national.consumer_queue_2.infrastructure.adapters.out.database.filesystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transp_national.consumer_queue_2.application.ports.out.SaveScheduleFilePort;
import com.transp_national.consumer_queue_2.domain.model.Schedule;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class LocalFileSystemScheduleAdapter implements SaveScheduleFilePort {

    private final ObjectMapper objectMapper;
    private final String OUTPUT_DIR = "schedules_output"; // Carpeta donde se guardarán los JSON

    public LocalFileSystemScheduleAdapter() {
        this.objectMapper = new ObjectMapper();

        // Crear la carpeta si no existe
        File directory = new File(OUTPUT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public void saveToFile(Schedule schedule) {
        try {
            // Generamos un nombre de archivo único usando la ruta y la hora actual
            String fileName = "schedule_" + schedule.routeId() + "_" + System.currentTimeMillis() + ".json";
            File file = new File(OUTPUT_DIR, fileName);

            // Escribimos el objeto como JSON en el archivo
            objectMapper.writeValue(file, schedule);
            System.out.println("Archivo JSON generado exitosamente en: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al generar el archivo JSON: " + e.getMessage());
        }
    }
}