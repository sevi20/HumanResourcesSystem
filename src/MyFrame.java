import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyFrame extends JFrame{
	
	JTabbedPane tab = new JTabbedPane();

	Connection conn = null; 
	PreparedStatement state = null;
	ResultSet result = null;
	int id = -1;
	
	List<Long> idPositon = new ArrayList<>();
	List<Long> idContract = new ArrayList<>();
	List<Long> idEmployee = new ArrayList<>();
	
	//---------------Positions-------------------------------
	JTable posTable = new JTable(); 
	JScrollPane scrollerP = new JScrollPane(posTable);
	
	JButton addPosition = new JButton("Добавяне");
	JButton editPosition = new JButton("Редактиране");
	JButton deletePosition = new JButton("Изтриване");
	JButton searchPosition = new JButton("Търсене");
	JButton refreshPosition = new JButton("Обновяване");
	JComboBox<String> searchPositionBox = new JComboBox<String>();
	
	
	JPanel positionP = new JPanel();
		JPanel positionUp = new JPanel(new GridLayout(3,1));
		JPanel positionMid = new JPanel();
		JPanel positionDown = new JPanel();
		JPanel positionSearchPanel = new JPanel();
		
		JLabel positionNameL = new JLabel("Позиции: ");
		JLabel searchPosL = new JLabel("Търси Позиция: ");
		JTextField positionNameTF = new JTextField();
		
	//---------------Positions END-------------------------------	
		
		
	//---------------Employees----------------------------------- 
		JTable empTable = new JTable();
		JScrollPane scrollerE = new JScrollPane(empTable);
		
		JButton addEmployee= new JButton("Добави");
		JButton editEmployee = new JButton("Редактиране");
		JButton deleteEmployee = new JButton("Изтриване");
		JButton searchEmployee = new JButton("Търсене");
		JButton refreshEmployee = new JButton("Обновяване");
		JComboBox<String> addPositionBox = new JComboBox<String>();
		JComboBox<String> addContractBox = new JComboBox<String>();
		JComboBox<String> searchEmployeeBox = new JComboBox<String>();
		
	JPanel employeeP = new JPanel();
		JPanel employeeUp = new JPanel(new GridLayout(6,1));
		JPanel employeeMid = new JPanel();
		JPanel employeeDown = new JPanel();
		JPanel employeeSearchPanel = new JPanel();
		
		JLabel empNameL = new JLabel("Име и Фамилия: ");
		JLabel empBirthL = new JLabel("Дата на раждане: ");
		JLabel empEmailL = new JLabel("E-mail: ");
		JLabel empCityL = new JLabel("Град: ");
		JLabel empPosL = new JLabel("Позиция: ");
		JLabel empConL = new JLabel("Тип договор: ");
		JLabel searchEmpL = new JLabel("Търси служител: ");
		
		JTextField empNameTF = new JTextField();
		JTextField empBirthTF = new JTextField("yyyy-MM-dd");
		JTextField empEmailTF = new JTextField();
		JTextField empCityTF = new JTextField();
		
	//---------------Employees----------------------------------- 	
		
		
		
	//------------------Contract--------------------------
		JTable conTable = new JTable(); 
		JScrollPane scrollerC = new JScrollPane(conTable);
		
	JPanel contractP = new JPanel();
		JPanel contractUp = new JPanel(new GridLayout(3,1));
		JPanel contractMid = new JPanel();
		JPanel contractDown = new JPanel();
		JPanel contractSearchPanel = new JPanel();
	
		JLabel contractTypeL = new JLabel("Тип договор: ");
		JLabel hireDateL = new JLabel("Дата на наемане: ");
		JLabel searchConL = new JLabel("Търси договор: ");
		
		JTextField contractTypeTF = new JTextField();
		JTextField hireDateTF = new JTextField("yyyy-MM-dd");
		
		JButton addContract = new JButton("Добавяне");
		JButton editContract = new JButton("Редактиране");
		JButton deleteContract = new JButton("Изтриване");
		JButton searchContract = new JButton("Търсене");
		JButton refreshContract = new JButton("Обновяване");
		JComboBox<String> searchContractBox = new JComboBox<String>();
		
	//------------------Contract END---------------------------	
		
		
	//------------------Reference-------------------------------
		JTable referenceTable = new JTable(); 
		JScrollPane scrollerReference = new JScrollPane(referenceTable);
		
		JPanel referenceP = new JPanel();
			JPanel referenceUp = new JPanel(new GridLayout(3,1));
			JPanel referenceMid = new JPanel();
			JPanel referenceDown = new JPanel();
			
		JLabel referencePositionL = new JLabel("Позиция във фирмата: ");
		JLabel referenceContractL = new JLabel("Тип договор: ");
		JComboBox<String> referencePositionBox = new JComboBox<String>();
		JComboBox<String> referenceContractBox = new JComboBox<String>();
		
		JButton searchReference = new JButton("Търсене");
			
	//----------------Reference END--------------------------
		
	
	public MyFrame() {
		DBHelper.init();
		this.setResizable(false);
		tab.add("Позиции", positionP);
		tab.add("Служители",employeeP);
		tab.add("Договор",contractP);
		tab.add("Справка", referenceP);
		tab.setSize(500, 600);
		
		//----------Positions Panels--------------
		positionP.setLayout(new GridLayout(4,1));
		
		positionP.add(positionUp);
		positionP.add(positionMid);
		positionP.add(positionDown);
		positionP.add(positionSearchPanel);
		//up
		positionUp.add(positionNameL);
		positionUp.add(positionNameTF);
		//mid
		positionMid.add(addPosition);
		positionMid.add(editPosition);
		positionMid.add(deletePosition);
		positionMid.add(refreshPosition);
		
		addPosition.addActionListener(new AddActionPosition());
		deletePosition.addActionListener(new DeleteActionPosition());
		editPosition.addActionListener(new EditActionPosition());
		searchPosition.addActionListener(new SearchActionPosition());
		refreshPosition.addActionListener(new RefreshPosition());
		
		//down
		positionDown.add(scrollerP);
		scrollerP.setPreferredSize(new Dimension(480, 130));
		posTable.setModel(DBHelper.getAllData("POSITIONS"));
		posTable.removeColumn(posTable.getColumnModel().getColumn(0)); //така махаме колоната ДОКАТО пазим данните в нея
		posTable.addMouseListener(new TableListener());
		
		//positionSearchPanel
		positionSearchPanel.add(searchPosL);
		positionSearchPanel.add(searchPositionBox);
		positionSearchPanel.add(searchPosition);
		
		DBHelper.fillPositionCombo(searchPositionBox, idPositon);
	
		//---------------Positions Panels END-----------------------
		
		
		//---Employees Panels---
		employeeP.setLayout(new GridLayout(4,1));
		
		employeeP.add(employeeUp);
		employeeP.add(employeeMid);
		employeeP.add(employeeDown);
		employeeP.add(employeeSearchPanel);
		
		//up
		employeeUp.add(empNameL);
		employeeUp.add(empNameTF);
		employeeUp.add(empBirthL);
		employeeUp.add(empBirthTF);
		employeeUp.add(empEmailL);
		employeeUp.add(empEmailTF);
		employeeUp.add(empCityL);
		employeeUp.add(empCityTF);
		employeeUp.add(empPosL);
		employeeUp.add(addPositionBox);
		employeeUp.add(empConL);
		employeeUp.add(addContractBox);
		
		//mid
		employeeMid.add(addEmployee);
		employeeMid.add(editEmployee);
		employeeMid.add(deleteEmployee);
		employeeMid.add(refreshEmployee);
		
		addEmployee.addActionListener(new AddActionEmployee());
		refreshEmployee.addActionListener(new RefreshEmployee());
		deleteEmployee.addActionListener(new DeleteActionEmployee());
		editEmployee.addActionListener(new EditActionEmployee());
		searchEmployee.addActionListener(new SearchActionEmployee());
		
		//down
		employeeDown.add(scrollerE);
		scrollerE.setPreferredSize(new Dimension(480, 130));
		empTable.setModel(DBHelper.getEmployeeData());
		empTable.removeColumn(empTable.getColumnModel().getColumn(0)); 
		empTable.addMouseListener(new TableListenerEmp());
		
		//employeeSearchPanel
		employeeSearchPanel.add(searchEmpL);
		employeeSearchPanel.add(searchEmployeeBox);
		employeeSearchPanel.add(searchEmployee);
		
		DBHelper.fillPositionCombo(addPositionBox, idPositon);
		DBHelper.fillContractCombo(addContractBox, idContract);
		DBHelper.fillEmployeeCombo(searchEmployeeBox, idEmployee);
		
		//--Employees Panels END---
		
		
		
		//-------------Contract Panels------------------	
		contractP.setLayout(new GridLayout(4,1));
		
		contractP.add(contractUp);
		contractP.add(contractMid);
		contractP.add(contractDown);
		contractP.add(contractSearchPanel);
		
		//up
		contractUp.add(contractTypeL);
		contractUp.add(contractTypeTF);
		contractUp.add(hireDateL);
		contractUp.add(hireDateTF);
		
		//mid
		contractMid.add(addContract);
		contractMid.add(editContract);
		contractMid.add(deleteContract);
		contractMid.add(refreshContract);
		
		addContract.addActionListener(new AddActionContract());
		refreshContract.addActionListener(new RefreshContarct());
		deleteContract.addActionListener(new DeleteActionContarct());
		editContract.addActionListener(new EditActionContract());
		searchContract.addActionListener(new SearchActionContract());
		
		//down
		contractDown.add(scrollerC);
		scrollerC.setPreferredSize(new Dimension(480, 130));
		conTable.setModel(DBHelper.getAllData("CONTRACT"));
		conTable.removeColumn(conTable.getColumnModel().getColumn(0));
		conTable.addMouseListener(new TableListenerCon());
		
		//positionSearchPanel
		contractSearchPanel.add(searchConL);
		contractSearchPanel.add(searchContractBox);
		contractSearchPanel.add(searchContract);
		
		DBHelper.fillContractCombo(searchContractBox, idContract);
		//-------------Contract Panels END--------------------------
		
		
		//-------------Reference Panels------------------------------
		referenceP.setLayout(new GridLayout(3,1));
		
		referenceP.add(referenceUp);
		referenceP.add(referenceMid);
		referenceP.add(referenceDown);
		
		//up
		referenceUp.add(referencePositionL);
		referenceUp.add(referencePositionBox);
		referenceUp.add(referenceContractL);
		referenceUp.add(referenceContractBox);

		//mid
		referenceMid.add(searchReference);
		searchReference.addActionListener(new SearchActionReference());
		
		//down
		referenceDown.add(scrollerReference);
		scrollerReference.setPreferredSize(new Dimension(480, 130));
		
		DBHelper.fillReferenceComboPos(referencePositionBox);
		DBHelper.fillReferenceComboCon(referenceContractBox);
		
		//-------------Reference Panels END--------------------------
		
		
		this.add(tab);
		this.setSize(500, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	//-------------Reference Action--------------------------
	
	class SearchActionReference implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			conn = DBHelper.getConnection();
			String sql = "SELECT NAME, POSITION_NAME, CONTRACT_TYPE FROM EMPLOYEES E\r\n" + 
					"					JOIN POSITIONS P ON E.POSITION_ID = P. POSITION_ID\r\n" + 
					"					JOIN CONTRACT C ON E.CONTRACT_ID = C.CONTRACT_ID\r\n" + 
					"WHERE POSITION_NAME = ? AND CONTRACT_TYPE = ?";
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, referencePositionBox.getSelectedItem().toString());
				state.setString(2, referenceContractBox.getSelectedItem().toString());
				
				result = state.executeQuery();
				referenceTable.setModel(new MyModel(result));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}//end searchAction
		
	//------------Reference Action END------------------------

	//---Employees Actions---
	
	public void ClearFormEmployee() {
		empNameTF.setText("");
		empBirthTF.setText("");
		empEmailTF.setText("");
		empCityTF.setText("");
	}// end clearForm
	
	class RefreshEmployee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			empTable.setModel(DBHelper.getEmployeeData());
			empTable.removeColumn(empTable.getColumnModel().getColumn(0));
		}	
	}
	
	class AddActionEmployee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			conn=DBHelper.getConnection();
			String sql="INSERT INTO EMPLOYEES VALUES (null, ?, ?, ?, ?, ?, ?)";
			try {
				state=conn.prepareStatement(sql);
				state.setString(1, empNameTF.getText());
				state.setDate(2, Date.valueOf(empBirthTF.getText()));
				state.setString(3, empEmailTF.getText());
				state.setString(4, empCityTF.getText());
				state.setLong(5, idPositon.get(addPositionBox.getSelectedIndex()));
				state.setLong(6, idContract.get(addContractBox.getSelectedIndex()));
				
				state.execute();
				DBHelper.fillEmployeeCombo(searchEmployeeBox, idEmployee);
				empTable.setModel(DBHelper.getEmployeeData());
				empTable.removeColumn(empTable.getColumnModel().getColumn(0));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ClearFormEmployee();
		}
		
	}// end class AddAction
 
	class TableListenerEmp implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = empTable.getSelectedRow(); // запазва селектрирания ред
			id = Integer.parseInt(empTable.getModel().getValueAt(row, 0).toString());
			
			if(e.getClickCount()==1){
				id = Integer.parseInt(empTable.getModel().getValueAt(row, 0).toString());
		        }
			 
			if(e.getClickCount()==2){
		           empNameTF.setText(empTable.getModel().getValueAt(row, 1).toString());
		           empBirthTF.setText(empTable.getModel().getValueAt(row, 2).toString());
		           empEmailTF.setText(empTable.getModel().getValueAt(row, 3).toString());
		           empCityTF.setText(empTable.getModel().getValueAt(row, 4).toString());
		           addPositionBox.setSelectedItem((empTable.getModel().getValueAt(row, 5).toString()));
		           addContractBox.setSelectedItem((empTable.getModel().getValueAt(row, 6).toString()));
		        }
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

			
	}
	
	class DeleteActionEmployee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBHelper.getConnection();
			String sql = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				empTable.setModel(DBHelper.getEmployeeData());
				DBHelper.fillEmployeeCombo(searchEmployeeBox, idEmployee);
				id = -1;
				empTable.removeColumn(empTable.getColumnModel().getColumn(0));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}//end method
		
	}//end DeleteAction

	class EditActionEmployee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			int row = empTable.getSelectedRow();
			id = Integer.parseInt(empTable.getModel().getValueAt(row, 0).toString());
			
			conn = DBHelper.getConnection();
			String sql = "UPDATE EMPLOYEES SET NAME = ?, DOB = ?, EMAIL = ?, CITY = ?, POSITION_ID = ?, CONTRACT_ID = ?  WHERE EMPLOYEE_ID =" + id;
		 
		 try {
			state = conn.prepareStatement(sql);
			state.setString(1, empNameTF.getText());
			state.setDate(2, Date.valueOf(empBirthTF.getText()));
			state.setString(3, empEmailTF.getText());
			state.setString(4, empCityTF.getText());
			state.setLong(5, idPositon.get(addPositionBox.getSelectedIndex()));
			state.setLong(6, idContract.get(addContractBox.getSelectedIndex()));
			
			state.execute();
			empTable.setModel(DBHelper.getEmployeeData());
			DBHelper.fillEmployeeCombo(searchEmployeeBox, idEmployee);
			empTable.removeColumn(empTable.getColumnModel().getColumn(0));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 ClearFormEmployee();
	}
} //end editAction
	
	class SearchActionEmployee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String item = searchEmployeeBox.getSelectedItem().toString();
			String[] content = item.split(" ");
			int employeeId = Integer.parseInt(content[0]);
			
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, employeeId);
				result = state.executeQuery();
				empTable.setModel(new MyModel(result)); // попълваме таблицата
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}//end addComboAction
	
	//---Employees Actions---
	
	
	
	//-------------Contract Actions--------------------
	
	public void ClearFormContract() {
		contractTypeTF.setText("");
		hireDateTF.setText("");
	} //end ClearForm
	
	class AddActionContract implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			conn=DBHelper.getConnection();
			String sql="INSERT INTO CONTRACT VALUES(null,?,?)";
			try {
				state=conn.prepareStatement(sql);
				state.setString(1, contractTypeTF.getText());
				state.setDate(2, Date.valueOf(hireDateTF.getText()));
				state.execute();
				conTable.setModel(DBHelper.getAllData("CONTRACT"));
				conTable.removeColumn(conTable.getColumnModel().getColumn(0));
				DBHelper.fillContractCombo(searchContractBox, idContract);
				DBHelper.fillContractCombo(addContractBox, idContract);
				DBHelper.fillReferenceComboCon(referenceContractBox);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ClearFormContract();
		}
		
	}// end class AddAction

	class RefreshContarct implements ActionListener{ 

		@Override
		public void actionPerformed(ActionEvent e) {
			conTable.setModel(DBHelper.getAllData("CONTRACT"));
			conTable.removeColumn(conTable.getColumnModel().getColumn(0));
		}	
	} //end Refresh
	
	class TableListenerCon implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = conTable.getSelectedRow(); // запазва селектрирания ред
			id = Integer.parseInt(conTable.getModel().getValueAt(row, 0).toString());
			
			if(e.getClickCount()==1){
				id = Integer.parseInt(conTable.getModel().getValueAt(row, 0).toString());
		        }
			 
			if(e.getClickCount()==2){
		           contractTypeTF.setText(conTable.getModel().getValueAt(row, 1).toString());
		           hireDateTF.setText(conTable.getModel().getValueAt(row, 2).toString());
		        }
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


	}//end TableListenerCon
	
	class DeleteActionContarct implements ActionListener{ 

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBHelper.getConnection();
			String sql = "DELETE FROM CONTRACT WHERE CONTRACT_ID = ?";
			try {
				state = conn.prepareStatement(sql);
				//TODO fill param
				state.setInt(1, id);
				state.execute();
				conTable.setModel(DBHelper.getAllData("CONTRACT"));
				id = -1;
				conTable.removeColumn(conTable.getColumnModel().getColumn(0));
				DBHelper.fillContractCombo(searchContractBox, idContract);
				DBHelper.fillContractCombo(addContractBox, idContract);
				DBHelper.fillReferenceComboCon(referenceContractBox);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}//end method
		
	}//end DeleteAction
	
	class EditActionContract implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			int row = conTable.getSelectedRow();
			id = Integer.parseInt(conTable.getModel().getValueAt(row, 0).toString());
			
		 conn = DBHelper.getConnection();
		 String sql = "UPDATE CONTRACT SET CONTRACT_TYPE = ?, HIRE_DATE = ? WHERE CONTRACT_ID =" + id ;
		 
		 try {
			state = conn.prepareStatement(sql);
			state.setString(1, contractTypeTF.getText());
			state.setDate(2, Date.valueOf(hireDateTF.getText()));
			state.execute();
			conTable.setModel(DBHelper.getAllData("CONTRACT"));
			conTable.removeColumn(conTable.getColumnModel().getColumn(0));
			DBHelper.fillContractCombo(searchContractBox, idContract);
			DBHelper.fillContractCombo(addContractBox, idContract);
			DBHelper.fillReferenceComboCon(referenceContractBox);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 ClearFormContract();
	}
} //end editAction
	
	class SearchActionContract implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM CONTRACT WHERE CONTRACT_ID=?";
			try {
				state = conn.prepareStatement(sql);
				state.setLong(1, idContract.get(searchContractBox.getSelectedIndex()));
				result = state.executeQuery();
				conTable.setModel(new MyModel(result));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}//end searchAction
	
	//-------------Contract Actions END--------------------	
	
	
	
	//---------------Position Actions---------------------
	public void ClearFormPosition() {
		positionNameTF.setText("");
	}
	
	class RefreshPosition implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			posTable.setModel(DBHelper.getAllData("POSITIONS"));
			posTable.removeColumn(posTable.getColumnModel().getColumn(0));
		}	
	}
	
	class SearchActionPosition implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBHelper.getConnection();
			String sql = "SELECT * FROM POSITIONS WHERE POSITION_ID=?";
			try {
				state = conn.prepareStatement(sql);
				state.setLong(1, idPositon.get(searchPositionBox.getSelectedIndex()));
				result = state.executeQuery();
				posTable.setModel(new MyModel(result));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}//end addComboAction
	
	class TableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = posTable.getSelectedRow(); // запазва селектрирания ред
			id = Integer.parseInt(posTable.getModel().getValueAt(row, 0).toString());
			
			if(e.getClickCount()==1){
				id = Integer.parseInt(posTable.getModel().getValueAt(row, 0).toString());
		        }
			 
			if(e.getClickCount()==2){
		           positionNameTF.setText(posTable.getModel().getValueAt(row, 1).toString());
		        }
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}//end TableListener
	
	class DeleteActionPosition implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBHelper.getConnection();
			String sql = "DELETE FROM POSITIONS WHERE POSITION_ID = ?";
			try {
				state = conn.prepareStatement(sql);
				//TODO fill param
				state.setInt(1, id);
				state.execute();
				posTable.setModel(DBHelper.getAllData("POSITIONS"));
				DBHelper.fillPositionCombo(searchPositionBox,idPositon);
				DBHelper.fillPositionCombo(addPositionBox,idPositon);
				DBHelper.fillReferenceComboPos(referencePositionBox);
				id = -1;
				posTable.removeColumn(posTable.getColumnModel().getColumn(0));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}//end method
		
	}//end DeleteAction
	
	class AddActionPosition implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			conn=DBHelper.getConnection();
			String sql="INSERT INTO POSITIONS VALUES(null,?)";
			try {
				state=conn.prepareStatement(sql);
				state.setString(1, positionNameTF.getText());
				state.execute();
				posTable.setModel(DBHelper.getAllData("POSITIONS"));
				posTable.removeColumn(posTable.getColumnModel().getColumn(0));
				DBHelper.fillPositionCombo(searchPositionBox,idPositon);
				DBHelper.fillPositionCombo(addPositionBox,idPositon); //обновява и комбо бокса в служители
				DBHelper.fillReferenceComboPos(referencePositionBox);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ClearFormPosition();
		}
		
	}// end class AddAction

	class EditActionPosition implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			int row = posTable.getSelectedRow();
			id = Integer.parseInt(posTable.getModel().getValueAt(row, 0).toString());
			
		 conn = DBHelper.getConnection();
		 String sql = "UPDATE POSITIONS SET POSITION_NAME = ? WHERE POSITION_ID =" + id ;
		 
		 try {
			state = conn.prepareStatement(sql);
			state.setString(1, positionNameTF.getText());
			state.execute();
			posTable.setModel(DBHelper.getAllData("POSITIONS"));
			posTable.removeColumn(posTable.getColumnModel().getColumn(0));
			DBHelper.fillPositionCombo(searchPositionBox,idPositon);
			DBHelper.fillPositionCombo(addPositionBox,idPositon);
			DBHelper.fillReferenceComboPos(referencePositionBox);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 ClearFormPosition();
	}
		
}
	//---------------Position Actions END---------------------


}// end class MyFrame
