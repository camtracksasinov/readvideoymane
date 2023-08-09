//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class UserAccountStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean blocks;

	public UserAccountStatus(final boolean blocks) {
		this.blocks = blocks;
	}

	public boolean isBlocks() {
		return this.blocks;
	}

	public void setBlocks(final boolean blocks) {
		this.blocks = blocks;
	}
}
