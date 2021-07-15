package it.univaq.esc.EntityDTOMappers;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.StrutturaPolisportivaDTO;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.prenotazioni.Appuntamento;

public abstract class StrutturaPolisportivaMapper extends EntityDTOMapper{

	public StrutturaPolisportivaDTO convertiInStrutturaPolisportivaDTO(StrutturaPolisportiva strutturaPolisportiva) {
		StrutturaPolisportivaDTO strutturaDTO = new StrutturaPolisportivaDTO();
		strutturaDTO.setIdStruttura(strutturaPolisportiva.getIdNotificabile());
		
		for(Appuntamento appuntamento : strutturaPolisportiva.getListaAppuntamenti()){
        	impostaMapperFactory(appuntamento.getModalitaPrenotazione());
        	AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
            strutturaDTO.getAppuntamenti().add(appuntamentoDTO);
        }
        return strutturaDTO;
	}
	
	public abstract StrutturaPolisportiva convertiDTOInStrutturaPolisportiva(StrutturaPolisportivaDTO strutturaDTO);
}
