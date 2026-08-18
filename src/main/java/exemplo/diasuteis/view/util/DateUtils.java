package exemplo.diasuteis.view.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class DateUtils {

    public static int getWorkingDays(LocalDate initialDate, LocalDate finalDate) {
        return getWorkingDays(initialDate, finalDate, Collections.emptyList());
    }

    public static int getWorkingDays(LocalDate initialDate, LocalDate finalDate, Collection<LocalDate> feriados) {

        if (initialDate == null || finalDate == null || finalDate.isBefore(initialDate)) {
            return 0;
        }

        int workingDays = 0;

        for (LocalDate dia = initialDate; !dia.isAfter(finalDate); dia = dia.plusDays(1)) {
            boolean fimDeSemana = dia.getDayOfWeek() == DayOfWeek.SATURDAY || dia.getDayOfWeek() == DayOfWeek.SUNDAY;
            boolean feriado = feriados.contains(dia);

            if (!fimDeSemana && !feriado) {
                workingDays++;
            }
        }

        return workingDays;
    }
}
