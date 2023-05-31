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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<PagoEntity> buscarPagos(String codigo){
        return pagoRepository.findPagoByCodigo(codigo);
    }

    public List<ProveedorModel> findAllProveedores(){
        return restTemplate.getForObject("http://localhost:8093/proveedores", List.class);
    }
    public ProveedorModel findProveedorByCodigoProveedor(String codigo){
        return restTemplate.getForObject("http://localhost:8093/proveedores/"+ codigo, ProveedorModel.class);
    }

    public ArrayList<LlegadaModel> findAllLlegadasByCodigoProveedor(String codigo){
        return restTemplate.getForObject("http://localhost:8091/llegadas/"+ codigo, ArrayList.class);
    }

    public Integer getTotalDays(String codigo){
        return restTemplate.getForObject("http://localhost:8091/llegadas/totalDays/"+ codigo, Integer.class);
    }

    public ResultadoModel getResultadoByCodigo(String codigo){
        return restTemplate.getForObject("http://localhost:8092/resultados/"+ codigo, ResultadoModel.class);
    }

    public Integer getTotalTurnos(String codigo, String turnos){
        return restTemplate.getForObject("http://localhost:8091/llegadas/totalTurnos/"+ codigo + "/" + turnos, Integer.class);
    }

    public void imprimirCodigoProveedores(){
        List<ProveedorModel> proveedores = findAllProveedores();
        for (ProveedorModel proveedor: proveedores) {
            System.out.println(proveedor.getCodigo());
        }
    }

    @Generated
    public void setPago(String codigo) throws ParseException {
        ProveedorModel proveedor = findProveedorByCodigoProveedor(codigo);
        PagoEntity pago = new PagoEntity();
        //----------- Atributos Basicos-----------------
        //codigo proveedor
        pago.setCodigoProveedor(codigo);
        //nombre proveedor
        pago.setNombreProveedor(proveedor.getNombre());
        //----------- Quincena ----------------
        ArrayList<LlegadaModel> llegadas = findAllLlegadasByCodigoProveedor(codigo);
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
        double promedioDiarioKlsLeche = totalKgLeche/numeroDiasEnvio;
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
        for(int i = 0; i < llegadas.size(); i++){
            total = total + (llegadas.get(i).getKg_leche());
        }
        return total;
    }

    public Integer pagoPorCategoria(ArrayList<LlegadaModel> llegadas, String categoriaLetra){
        // Pago por kilo de leche según categoría
        int categoria;
        if(categoriaLetra.equals("A")){
            categoria = 700;
        }else if(categoriaLetra.equals("B")){
            categoria = 550;
        }else if(categoriaLetra.equals("C")){
            categoria = 400;
        }else{
            categoria = 250;
        }
        // Acumulador de pago en categoría
        int pagoCategoria =0;
        for(int i = 0; i < llegadas.size(); i++){
            pagoCategoria = pagoCategoria + (categoria * llegadas.get(i).getKg_leche());
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
        for(int i = 0; i < llegadas.size(); i++){
            pagoGrasa = pagoGrasa + (grasa * llegadas.get(i).getKg_leche());
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
        for(int i = 0; i < llegadas.size(); i++){
            pagoSolidos = pagoSolidos + (solidos * llegadas.get(i).getKg_leche());
        }
        return pagoSolidos;
    }

    public Double bonificacionFecuencia(String codigo, Integer pagoCategoria){
        int mSum = getTotalTurnos(codigo, "M");
        int tSum = getTotalTurnos(codigo, "T");
        Double bonificacionFrecuencia = 0.0;
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

    public Double porcentajeVariacionLeche(PagoEntity pagoAnterior, Integer totalKgLeche){
        Double porcentajeVariacionLeche = 0.0;
        if(pagoAnterior.getId_pago() != 0){
            Double kgLecheAnterior = pagoAnterior.getTotalKlsLeche();
            porcentajeVariacionLeche = (((kgLecheAnterior-totalKgLeche)/totalKgLeche)*100);
        }
        return porcentajeVariacionLeche;
    }

    public Double porcentajeVariacionGrasa(PagoEntity pagoAnterior, Integer resultadoGrasa){
        Double porcentajeVariacionGrasa = 0.0;
        if(pagoAnterior.getId_pago() != 0){
            if(pagoAnterior.getPorcentajeGrasa() != 0){
                Double grasaAnterior = pagoAnterior.getPorcentajeGrasa();
                porcentajeVariacionGrasa = ((grasaAnterior-resultadoGrasa)/resultadoGrasa);
            }
        }
        return porcentajeVariacionGrasa;
    }

    public Double porcentajeVariacionSolidos(PagoEntity pagoAnterior, Integer resultadoSolidos){
        Double porcentajeVariacionSolidos = 0.0;
        if(pagoAnterior.getId_pago() != 0){
            if(pagoAnterior.getPorcentajeSolidosTotales() != 0){
                Double solidosAnterior = pagoAnterior.getPorcentajeSolidosTotales();
                porcentajeVariacionSolidos = ((solidosAnterior-resultadoSolidos)/resultadoSolidos);
            }
        }
        return porcentajeVariacionSolidos;
    }

    public Double descuentoVariacionLeche(Double porcentajeVariacionLeche, Double pagoAcopio) {
        Double descuentoVariacionLeche;
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

    public Double descuentoVariacionGrasa(Double porcentajeVariacionGrasa, Double pagoAcopio) {
        Double descuentoVariacionGrasa;
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

    public Double descuentoVariacionSolidos(Double porcentajeVariacionSolidos, Double pagoAcopio) {
        Double descuentoVariacionSolidos;
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
