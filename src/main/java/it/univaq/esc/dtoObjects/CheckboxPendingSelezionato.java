package it.univaq.esc.dtoObjects;

public class CheckboxPendingSelezionato {
    
    private Integer idSelezione;

    private boolean pending;

    public CheckboxPendingSelezionato(){}

    public Integer getIdSelezione() {
        return idSelezione;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public void setIdSelezione(Integer idSelezione) {
        this.idSelezione = idSelezione;
    }
}
