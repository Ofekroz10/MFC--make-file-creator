package guiPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWin implements Igui
{
	JFrame mainFrame;
	JFileChooser dirBox;
	JButton dirButton;
	JTextField dirText;
	String path;
	public MainWin()
	{
		mainFrame = new JFrame("MFC: Make File Creator");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dirBox = new JFileChooser();
		dirButton = new JButton("Select directory");
		dirText = new JTextField();
		CreateWin();

	}
	public static void main(String[] args) {
		 
		MainWin main = new MainWin();
	      main.show();
	   }

	private void CreateWin() 
	{
		mainFrame.setSize(1000,850);
		JPanel upPanel = new JPanel();
		JLabel folderL = new JLabel("Choose the path of the folder");
		dirText.setText("Empty directory");
		dirText.setColumns(30);
		upPanel.add(folderL);
		upPanel.add(dirText);
		upPanel.add(dirButton);
		mainFrame.add(upPanel);
		dirChooserClick();
		dirButtonClick();
		
	}
	private void dirButtonClick()
	{
		this.dirButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				dirBox.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dirBox.showOpenDialog(mainFrame);
			}
		});
		
	}
	private void dirChooserClick()
	{
		this.dirBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				path = dirBox.getSelectedFile().getPath();
				dirText.setText(path);
			}
		});
	}
	@Override
	public void show() {
		this.mainFrame.setVisible(true);
		
	}
}
