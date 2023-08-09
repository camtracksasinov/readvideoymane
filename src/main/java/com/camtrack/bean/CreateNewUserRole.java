//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.List;

import com.camtrack.config.Utils;

public class CreateNewUserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idrole;
	private Integer idtyperole;
	private List<Rightonmenu> listmenuwithright;
	private String newrolename;

	public Integer getIdrole() {
		return this.idrole;
	}

	public Integer getIdtyperole() {
		return this.idtyperole;
	}

	public List<Rightonmenu> getListmenuwithright() {
		return this.listmenuwithright;
	}

	// Utils.StringEscape(datedebut), Utils.StringEscape(datefin)
	public String getNewrolename() {
		return Utils.StringEscape(this.newrolename);
	}

	public void setIdrole(final Integer idrole) {
		this.idrole = idrole;
	}

	public void setIdtyperole(final Integer idtyperole) {
		this.idtyperole = idtyperole;
	}

	public void setListmenuwithright(final List<Rightonmenu> listmenuwithright) {
		this.listmenuwithright = listmenuwithright;
	}

	public void setNewrolename(final String newrolename) {
		this.newrolename = newrolename;
	}
}
