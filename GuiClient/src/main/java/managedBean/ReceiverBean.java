package managedBean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.example.dao.ReceiverDAORemote;
import com.example.dto.ReceiverDTO;

@Named(value = "receiverBean")
@SessionScoped
public class ReceiverBean implements Serializable {

	private static final long serialVersionUID = 1L;

	ReceiverDTO receiverDTO = new ReceiverDTO();

	@EJB
	ReceiverDAORemote receiverDAORemote;

	public ReceiverDTO getReceiverDTO() {
		return receiverDTO;
	}

	public void setReceiverDTO(ReceiverDTO receiverDTO) {
		this.receiverDTO = receiverDTO;
	}

	public String createReceiver() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			receiverDTO = receiverDAORemote.create(receiverDTO);
			return "/customer/thankYouPage.xhtml?faces-redirect=true";

		} catch (PersistenceException e) {
			facesContext.addMessage("createProductForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return null;
		}
	}
}
