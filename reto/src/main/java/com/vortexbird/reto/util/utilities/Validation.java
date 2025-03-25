package com.vortexbird.reto.util.utilities;

import com.vortexbird.reto.util.constants.Constants;

import java.util.List;

public class Validation {
    public static boolean isNullOrEmpty(final Object obj) {
        boolean result = Boolean.FALSE;
        if (obj == null) {
            result = Boolean.TRUE;
        } else if (obj instanceof String) {
            String objString = (String) obj;
            if (objString.trim().equals(Constants.EMPTY)) {
                result = Boolean.TRUE;
            }
        } else if (obj instanceof String[]) {
            String[] objString = (String[]) obj;
            if (objString.length == 0) {
                result = Boolean.TRUE;
            }
        } else if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            if (list.isEmpty()) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    /**
     * Método que valida si un objeto no es nulo.
     *
     * @param
     * @return boolean con el resultado de la operación.
     */
    public static boolean isNotNull(final Object obj) {
        return obj != null;
    }
}
