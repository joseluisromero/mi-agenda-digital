package com.agendadigital.util;

import java.util.Calendar;
import java.util.Date;

public class FechaUtil {
    public static Date sumarDiaFecha(Date date, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, dias);
        return calendar.getTime();
    }

    public static Date restarDiaFecha(Date date, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, -dias);
        return calendar.getTime();
    }

    public static Integer direfenciaDiaEntreFecha(Date dateFin, Date dateInicio) {
        int milisegundoPorDia = 86400000;
        Integer diferencia = (int) ((dateFin.getTime() - dateInicio.getTime()) / milisegundoPorDia);
        return diferencia;
    }
}
