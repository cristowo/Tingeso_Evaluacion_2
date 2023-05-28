package milkstgo.llegadaservice.services;

import milkstgo.llegadaservice.entities.LlegadaEntity;
import milkstgo.llegadaservice.repositories.LlegadaRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class LlegadaService {
    @Autowired
    LlegadaRepository llegadaRepository;
    private final Logger logger = LoggerFactory.getLogger(LlegadaService.class);
    public ArrayList<LlegadaEntity> obtenerDatos(){
        return (ArrayList<LlegadaEntity>) llegadaRepository.findAll();
    }
    @Generated
    public String guardar(MultipartFile archivo){
        String nombre = archivo.getOriginalFilename();
        if(nombre != null) {
            if (!archivo.isEmpty()) {
                try {
                    byte[] bytes = archivo.getBytes();
                    Path path = Paths.get(archivo.getOriginalFilename());
                    Files.write(path, bytes);
                    logger.info("Archivo Guardado");
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
            return "Archivo Guardado con exito";
        }
        else{
            return "Error al Guardar el Archivo";
        }
    }

    public void guardarDatos(LlegadaEntity datos){
        llegadaRepository.save(datos);
    }

    public void guardarDatosBD(String fecha, String turno, String proveedor, String kg_leche){
        LlegadaEntity aux = new LlegadaEntity();

        try{
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(fecha);
            aux.setFecha(date);
        }catch (ParseException ex){
            throw new RuntimeException(ex);
        }
        aux.setTurno(turno);
        aux.setProveedor(proveedor);
        aux.setKg_leche(Integer.parseInt(kg_leche));
        guardarDatos(aux);
    }
    @Generated
    public String leerCsv(String archivo){
        BufferedReader bf = null;
        // no se si sea recomendable borrarlo, pero son datos que ya no se ocuparan.
        llegadaRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(archivo));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if(count == 1){
                    count = 0;
                }else{
                    guardarDatosBD(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
                    temp = temp + "\n" + bfRead;
                }
            }
            return "Acopio cargado con exito";
        }catch(Exception e){
            return "Error al cargar el acopio";
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logger.error("ERROR", e);
                }
            }
        }
    }

}