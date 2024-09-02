package MODELS;

import java.io.Serializable;

public class Asignacion implements Serializable {
    private String codigoInvestigador;
    private String codigoMuestra;

    public Asignacion(String codigoInvestigador, String codigoMuestra) {
        this.codigoInvestigador = codigoInvestigador;
        this.codigoMuestra = codigoMuestra;
    }

    public String getCodigoInvestigador() {
        return codigoInvestigador;
    }

    public void setCodigoInvestigador(String codigoInvestigador) {
        this.codigoInvestigador = codigoInvestigador;
    }

    public String getCodigoMuestra() {
        return codigoMuestra;
    }

    public void setCodigoMuestra(String codigoMuestra) {
        this.codigoMuestra = codigoMuestra;
    }
}