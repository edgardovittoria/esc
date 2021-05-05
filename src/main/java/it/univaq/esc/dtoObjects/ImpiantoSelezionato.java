package it.univaq.esc.dtoObjects;

public class ImpiantoSelezionato {
    
    private Integer idSelezione;

    private Integer idImpianto;

    public Integer getIdSelezione() {
        return idSelezione;
    }

    public Integer getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(String idImpianto) {
        this.idImpianto = Integer.parseInt(idImpianto);
    }

    public void setIdSelezione(Integer idSelezione) {
        this.idSelezione = idSelezione;
    }
}
