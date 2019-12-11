package guiPack;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import mainPack.Creator;

public class MainWin implements Igui
{
	JFrame mainFrame;
	JFileChooser dirBox;
	JButton dirButton;
	JTextField dirText;
	String path;
	JList<String> files;
	Creator creator ;
	JPanel upPanel;
	JPanel middleP;
	JLabel filesL;
	JButton createButton;
	
	public MainWin()
	{
		mainFrame = new JFrame("MFC: Make File Creator");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dirBox = new JFileChooser("C:\\Users\\ofekroz\\Desktop\\New folder");
		dirButton = new JButton("Select directory");
		dirText = new JTextField();
		files = new JList();
		filesL = new JLabel("The files are: ");
		createButton = new JButton("Make!");
		CreateWin();

	}
	public static void main(String[] args) {
		 
		MainWin main = new MainWin();
	      main.show();
	   }

	private void CreateWin() 
	{
		mainFrame.setSize(1000,850);
		upPanel = new JPanel();
		JLabel folderL = new JLabel("Choose the path of the folder");
		dirText.setText("Empty directory");
		dirText.setColumns(30);
		upPanel.add(folderL);
		upPanel.add(dirText);
		upPanel.add(dirButton);
		mainFrame.add(filesL);
		mainFrame.add(files);
		mainFrame.add(createButton);
		mainFrame.add(upPanel);
		files.setBounds(400,50, 100, 19);
		filesL.setBounds(300,50, 100, 19);
		createButton.setBounds(550,50, 80, 19);
		setMidVisible(false);
		
		dirChooserClick();
		dirButtonClick();
		createBClick();
		
	}
	private void createBClick() {
		this.createButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Creator c = new Creator(path);
				c.creatAndSave();
			}
		});
		
	}
	private void setMidVisible(boolean b) {
		files.setVisible(b);
		filesL.setVisible(b);
		createButton.setVisible(b);
		
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
				try 
				{
					path = dirBox.getSelectedFile().getPath();
					dirText.setText(path);
					//show c and h files
					creator = new Creator(path);
					showFiles();
					
				}
				catch(NullPointerException e1)
				{
					JOptionPane.showMessageDialog(mainFrame, "No path selected!");
					setMidVisible(false);
					
				}
			}

			private void showFiles() {
				try {
					creator.initCfiles();
					System.out.println(creator);
					if(creator.getAllFiles()==null)
					{
						JOptionPane.showMessageDialog(mainFrame, "There is no c and h files in: "+path);
						setMidVisible(false);
						return;
					}
						
					if(creator.getCfilesLen() ==0)
					{
						JOptionPane.showMessageDialog(mainFrame, "There is no c files in: "+path);
						setMidVisible(false);
						return;
					}
					String[] filesN = creator.getListF();
					files.setListData(filesN);
					files.setBounds(400,50, 100, 18*filesN.length);
					files.setCellRenderer(new DefaultListCellRenderer() {

	                    @Override
	                    public Component getListCellRendererComponent(JList list,
	                            Object value, int index, boolean isSelected,
	                            boolean cellHasFocus) {

	                        super.getListCellRendererComponent(list, value, index,
	                                isSelected, cellHasFocus);

	                        String name = (String) value;

	                       switch(name.charAt(name.length()-1))
	                       {
	                       		case 'c':
	                       		{
	                       			setBackground(Color.yellow);
	                       			break;
	                       		}
	                       		case 'h':
	                       		{
	                       			setBackground(Color.green);
	                       			break;
	                       		}
	                       		default:
	                       			setBackground(Color.cyan);
	                       			break;
	                        }

	                        return this;
	                    }
	                });
					
					setMidVisible(true);
					
				}
				catch(IOException e1)
				{
					JOptionPane.showMessageDialog(mainFrame, "Eror to read from the directory: "+path);
				}
				
			}
		});
	}
	@Override
	public void show() {
		this.mainFrame.setVisible(true);
		
	}
}
