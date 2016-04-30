package com.example.drock.n_corder.fileselector;

public interface OnHandleFileListener {
	/**
	 * This method is called after clicking the Save or Load button on the
	 * dialog_file_selector, if the file name was correct. It should be used to save or load a
	 * file using the filePath path.
	 * 
	 * @param filePath
	 *            File path set in the dialog_file_selector when the Save or Load button was
	 *            clicked.
	 */
	void handleFile(String filePath);
}