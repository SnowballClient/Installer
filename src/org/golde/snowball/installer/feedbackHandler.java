package org.golde.snowball.installer;

public class feedbackHandler {
	
	private Installer installer;
	
    public feedbackHandler(Installer installer) {
		this.installer = installer;
	}

	public void close() {
		installer.stop();
    }
}
