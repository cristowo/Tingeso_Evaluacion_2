package milkstgo.pagoservice.services;

import lombok.Generated;
import milkstgo.pagoservice.entities.PagoEntity;
import milkstgo.pagoservice.models.LlegadaModel;
import milkstgo.pagoservice.models.ProveedorModel;
import milkstgo.pagoservice.models.ResultadoModel;
import milkstgo.pagoservice.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<PagoEntity> buscarPagos(String codigo){
        return pagoRepository.findPagoByCodigo(codigo);
    }
    public List<ProveedorModel> findAllProveedores() {
        ProveedorModel[] proveedores = restTemplate.getForObject("http://proveedor-service/proveedores", ProveedorModel[].class);
        return Arrays.stream(proveedores).toList();
    }
    public ProveedorModel findProveedorByCodigoProveedor(String codigo){
        return restTemplate.getForObject("http://proveedor-service/proveedores/"+ codigo, ProveedorModel.class);
    }
    public ArrayList<LlegadaModel> findAllLlegadasByCodigoProveedor(String codigo){
        LlegadaModel[] llegada = restTemplate.getForObject("http://llegada-service/llegadas/"+ codigo, LlegadaModel[].class);
        return new ArrayList<>(Arrays.stream(llegada).toList());
    }
    public Integer getTotalDays(String codigo){
        return restTemplate.getForObject("http://llegada-service/llegadas/totalDays/"+ codigo, Integer.class);
    }
    public ResultadoModel getResultadoByCodigo(String codigo){
        return restTemplate.getForObject("http://resultado-service/resultados/"+ codigo, ResultadoModel.class);
    }
    public Integer getTotalTurnos(String codigo, String turnos){
        return restTemplate.getForObject("http://llegada-service/llegadas/totalTurnos/"+ codigo + "/" + turnos, Integer.class);
    }
    public List<PagoEntity> getAll(){
        return pagoRepository.getAll();
    }

    public void iniciar(){
        List<ProveedorModel> proveedores = findAllProveedores();
        for (ProveedorModel proveedor: proveedores) {
            try {
                if(findProveedorByCodigoProveedor(proveedor.getCodigo()) != null) {
                    setPago(proveedor);
                }
            } catch (ParseException e) {
                System.out.println("Proveedor no registrado " + proveedor.getCodigo());
            }
        }
    }

    @Generated
    public void setPago(ProveedorModel proveedor) throws ParseException {
        String codigo = proveedor.getCodigo();
        PagoEntity pago = new PagoEntity();
        //----------- Atributos Basicos-----------------
        //codigo proveedor
        pago.setCodigoProveedor(codigo);
        //nombre proveedor
        pago.setNombreProveedor(proveedor.getNombre());
        //----------- Quincena ----------------
        ArrayList<LlegadaModel> llegadas = findAllLlegadasByCodigoProveedor(codigo);
        if(llegadas.isEmpty()){return;}
        //Revisamos en que quincena nos encontramos
        String quincena = obtenerFechaQuincena(llegadas);
        pago.setQuincena(quincena);
        //------------- Kg Leche -------------------
        int totalKgLeche = totalKgLeche(llegadas);
        pago.setTotalKlsLeche(totalKgLeche);
        //---------- Numero de dias  ----------------
        int numeroDiasEnvio = getTotalDays(codigo);
        pago.setNumDiasEnvioLeche(numeroDiasEnvio);
        //------- Promedio Diario de Kg Leche  -------------
        double promedioDiarioKlsLeche = (double)totalKgLeche/(double)numeroDiasEnvio;
        pago.setPromedioDiarioKlsLeche(promedioDiarioKlsLeche);
        //------------ Porcentaje Grasa --------------------
        ResultadoModel resultado = getResultadoByCodigo(codigo);
        int porcentajeGrasa = resultado.getPorcentaje_grasa();
        pago.setPorcentajeGrasa(porcentajeGrasa);
        //------------ Porcentaje Solidos Totales --------------------
        int porcentajeSolidosTotales = resultado.getPorcentaje_solido();
        pago.setPorcentajeSolidosTotales(porcentajeSolidosTotales);
        //------------ Pago por Leche ------(categoria)-------
        double pagoPorLeche = pagoPorCategoria(llegadas, proveedor.getCategoria());
        pago.setPagoPorLeche(pagoPorLeche);
        //------------ Pago por Grasa --------------------
        double pagoPorGrasa = pagoPorGrasa(llegadas, resultado.getPorcentaje_grasa());
        pago.setPagoPorGrasa(pagoPorGrasa);
        //------------ Pago por Solidos Totales --------------------
        double pagoPorSolidosTotales = pagoPorSolidos(llegadas, resultado.getPorcentaje_solido());
        pago.setPagoPorSolidosTotales(pagoPorSolidosTotales);
        //------------ Bonificacion por Frecuencia --------------------
        double bonificacionFrecuencia = bonificacionFecuencia(codigo, (int) (pagoPorLeche));
        pago.setBonificacionPorFrecuencia(bonificacionFrecuencia);
        //---------- Porcentaje variacion leche -----------
        int idQuincenaAnterior = foundIdQuincenaAnterior(quincena, codigo);
        double pVariacionLeche = 0;
        double pVariacionGrasa = 0;
        double pVariacionSolidosTotales = 0;
        if(idQuincenaAnterior != 0){
            PagoEntity pagoAnterior = pagoRepository.findById(idQuincenaAnterior).get();
            //------------ Porcentaje variacion leche --------------------
            pVariacionLeche = porcentajeVariacionLeche(pagoAnterior, totalKgLeche);
            //------------ Porcentaje variacion grasa --------------------
            pVariacionGrasa = porcentajeVariacionGrasa(pagoAnterior, porcentajeGrasa);
            //------------ Porcentaje variacion solidos totales -----------
            pVariacionSolidosTotales = porcentajeVariacionSolidos(pagoAnterior, porcentajeSolidosTotales);
        }
        pago.setPorcentajeVariacionLeche(pVariacionLeche);
        pago.setPorcentajeVariacionGrasa(pVariacionGrasa);
        pago.setPorcentajeVariacionST(pVariacionSolidosTotales);
        //-------------- Pago Acopio Leche ----------------------
        double pagoAcopioLeche = pagoPorLeche + pagoPorGrasa + pagoPorSolidosTotales + bonificacionFrecuencia;
        //------------ Descuento Variacion Leche --------------------
        double dVariacionLeche = descuentoVariacionLeche(pVariacionLeche, pagoAcopioLeche);
        pago.setDescuentoVariacionLeche(dVariacionLeche);
        //------------ Descuento Variacion Grasa --------------------
        double dVariacionGrasa = descuentoVariacionGrasa(pVariacionGrasa, pagoAcopioLeche);
        pago.setDescuentoVariacionGrasa(dVariacionGrasa);
        //------------ Descuento Variacion Solidos Totales --------------------
        double dVariacionSolidosTotales = descuentoVariacionSolidos(pVariacionSolidosTotales, pagoAcopioLeche);
        pago.setDescuentoVariacionST(dVariacionSolidosTotales);
        //------------ Pago Total --------------------
        double pagoTotal = pagoAcopioLeche - (dVariacionLeche + dVariacionGrasa + dVariacionSolidosTotales);
        pago.setPagoTotal(pagoTotal);
        //------------ Monto Retencion --------------------
        double montoRetencion = montoRetencion(pagoTotal);
        pago.setMontoRetencion(montoRetencion);
        //------------ Monto Final --------------------
        double montoFinal = pagoTotal - montoRetencion;
        pago.setMontoFinal(montoFinal);
        System.out.println("pago se pago lol "+codigo);
        pagoRepository.save(pago);
    }

    public Double montoRetencion(Double pagoTotal){
        double montoRetencion = 0;
        if(pagoTotal > 950000){
            montoRetencion = pagoTotal * 0.13;
        }
        return montoRetencion;
    }

    public String obtenerFechaQuincena(ArrayList<LlegadaModel> llegadas){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(llegadas.get(0).getFecha());
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        if(dia <= 15){
            return Integer.toString(ano)+"/"+(Integer.toString(mes+1))+"/"+1;
        }else{
            return Integer.toString(ano)+"/"+(Integer.toString(mes+1))+"/"+2;
        }
    }

    public Integer totalKgLeche(ArrayList<LlegadaModel> llegadas){
        int total =0;
        for (LlegadaModel llegada : llegadas) {
            total = total + (llegada.getKg_leche());
        }
        return total;
    }

    public Integer pagoPorCategoria(ArrayList<LlegadaModel> llegadas, String categoriaLetra){
        // Pago por kilo de leche según categoría
        int categoria = switch (categoriaLetra) {
            case "A" -> 700;
            case "B" -> 550;
            case "C" -> 400;
            default -> 250;
        };
        // Acumulador de pago en categoría
        int pagoCategoria =0;
        for (LlegadaModel llegada : llegadas) {
            pagoCategoria = pagoCategoria + (categoria * llegada.getKg_leche());
        }
        return pagoCategoria;
    }

    public Integer pagoPorGrasa(ArrayList<LlegadaModel> llegadas, Integer resultadoGrasa){
        // Pago por grasa
        int grasa;
        if(resultadoGrasa <= 20){
            grasa = 30;
        }else if(resultadoGrasa <= 45){
            grasa = 80;
        }else{
            grasa = 120;
        }
        int pagoGrasa = 0;
        for (LlegadaModel llegada : llegadas) {
            pagoGrasa = pagoGrasa + (grasa * llegada.getKg_leche());
        }
        return pagoGrasa;
    }

    public Integer pagoPorSolidos(ArrayList<LlegadaModel> llegadas, Integer resultadoSolidos){
        // Pago por sólidos
        int solidos;
        if(resultadoSolidos <= 7){
            solidos = -130;
        }else if(resultadoSolidos <= 18){
            solidos = -90;
        }else if(resultadoSolidos <= 35){
            solidos = 95;
        }else{
            solidos = 150;
        }
        int pagoSolidos = 0;
        for (LlegadaModel llegada : llegadas) {
            pagoSolidos = pagoSolidos + (solidos * llegada.getKg_leche());
        }
        return pagoSolidos;
    }

    public double bonificacionFecuencia(String codigo, Integer pagoCategoria){
        int mSum = getTotalTurnos(codigo, "M");
        int tSum = getTotalTurnos(codigo, "T");
        double bonificacionFrecuencia = 0.0;
        if(mSum >= 10 && tSum >= 10){
            bonificacionFrecuencia = pagoCategoria * 0.2;
        }else if(mSum >= 10){
            bonificacionFrecuencia = pagoCategoria * 0.12;
        }else if(tSum >= 10){
            bonificacionFrecuencia = pagoCategoria * 0.08;
        }
        return bonificacionFrecuencia;
    }

    public Integer foundIdQuincenaAnterior(String quincena, String codigo) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(quincena);
        //obtenemos la fecha de la quincena anterior (sabiendo que dd es 1 o 2)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        String quincenaAnterior = "";
        if (dia == 1) {
            //caso especial de enero
            if (mes == 0) {
                quincenaAnterior = Integer.toString(ano - 1) + "/12/2";
            } else {
                quincenaAnterior = Integer.toString(ano) + "/" + Integer.toString(mes) + "/2";
            }
        } else {
            //vamos a la quincena anterior (mes y dia 1)
            quincenaAnterior = Integer.toString(ano) + "/" + Integer.toString(mes + 1) + "/1";
        }
        //caso no hay quincena anterior
        List<PagoEntity> quincenaEncontrada = pagoRepository.findPagolist(quincenaAnterior, codigo);
        if (quincenaEncontrada.size() == 0) {
            return 0;
        } else {
            //caso si hay quincena anterior
            return quincenaEncontrada.get(0).getId_pago();
        }
    }

    public double porcentajeVariacionLeche(PagoEntity pagoAnterior, Integer totalKgLeche){
        double porcentajeVariacionLeche = 0.0;
        if(pagoAnterior.getId_pago() != 0){
            double kgLecheAnterior = pagoAnterior.getTotalKlsLeche();
            porcentajeVariacionLeche = (((kgLecheAnterior-totalKgLeche)/totalKgLeche)*100);
        }
        return porcentajeVariacionLeche;
    }

    public double porcentajeVariacionGrasa(PagoEntity pagoAnterior, Integer resultadoGrasa){
        double porcentajeVariacionGrasa = 0.0;
        if(pagoAnterior.getId_pago() != 0){
            if(pagoAnterior.getPorcentajeGrasa() != 0){
                double grasaAnterior = pagoAnterior.getPorcentajeGrasa();
                porcentajeVariacionGrasa = ((grasaAnterior-resultadoGrasa)/resultadoGrasa);
            }
        }
        return porcentajeVariacionGrasa;
    }

    public double porcentajeVariacionSolidos(PagoEntity pagoAnterior, Integer resultadoSolidos){
        double porcentajeVariacionSolidos = 0.0;
        if(pagoAnterior.getId_pago() != 0){
            if(pagoAnterior.getPorcentajeSolidosTotales() != 0){
                double solidosAnterior = pagoAnterior.getPorcentajeSolidosTotales();
                porcentajeVariacionSolidos = ((solidosAnterior-resultadoSolidos)/resultadoSolidos);
            }
        }
        return porcentajeVariacionSolidos;
    }

    public double descuentoVariacionLeche(double porcentajeVariacionLeche, double pagoAcopio) {
        double descuentoVariacionLeche;
        if(porcentajeVariacionLeche <= 8) {
            descuentoVariacionLeche = pagoAcopio * 0;
        }else if(porcentajeVariacionLeche <= 25) {
            descuentoVariacionLeche = pagoAcopio * 0.07;
        }else if(porcentajeVariacionLeche <= 45) {
            descuentoVariacionLeche = pagoAcopio * 0.15;
        }else{
            descuentoVariacionLeche = pagoAcopio * 0.30;
        }
        return descuentoVariacionLeche;
    }

    public double descuentoVariacionGrasa(double porcentajeVariacionGrasa, double pagoAcopio) {
        double descuentoVariacionGrasa;
        if(porcentajeVariacionGrasa <= 15) {
            descuentoVariacionGrasa = pagoAcopio * 0;
        }else if(porcentajeVariacionGrasa <= 25) {
            descuentoVariacionGrasa = pagoAcopio * 0.12;
        }else if(porcentajeVariacionGrasa <= 40) {
            descuentoVariacionGrasa = pagoAcopio * 0.20;
        }else{
            descuentoVariacionGrasa = pagoAcopio * 0.30;
        }
        return descuentoVariacionGrasa;
    }

    public double descuentoVariacionSolidos(double porcentajeVariacionSolidos, double pagoAcopio) {
        double descuentoVariacionSolidos;
        if(porcentajeVariacionSolidos <= 6) {
            descuentoVariacionSolidos = pagoAcopio * 0;
        }else if(porcentajeVariacionSolidos <= 12) {
            descuentoVariacionSolidos = pagoAcopio * 0.18;
        }else if(porcentajeVariacionSolidos <= 35) {
            descuentoVariacionSolidos = pagoAcopio * 0.27;
        }else{
            descuentoVariacionSolidos = pagoAcopio * 0.45;
        }
        return descuentoVariacionSolidos;
    }

    public List<PagoEntity> pagoByquincena(String codigo, String quincena){
        List<PagoEntity> pago = pagoRepository.findPagolist(quincena, codigo);
        return pago;
    }

}
