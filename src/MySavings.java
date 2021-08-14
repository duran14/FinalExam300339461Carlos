import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * Created by JFormDesigner on Fri Aug 13 16:18:39 PDT 2021
 */



/**
 * @author unknown
 */
public class MySavings extends JFrame {

    //4. Instantiating and object named con
    //In the exam we will have to create a separate class with getters and setters instead
    Connection1 con = new Connection1();

    //5. Declaring a Connection object. Import class in connection and add exception in connect (mouse over)
    Connection conObj = con.connect();


    public static ArrayList<Savings> list1 = new ArrayList<Savings>();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//7. To show the form we need to instantiate a category
        //Add exception in Database (mouse over)
        MySavings form1 = new MySavings();

        //20. Calling the method that updates table
        form1.updateTable();

        //8. Make the form visible and run to see if it works
        form1.setVisible(true);


    }

    public void updateTable() throws SQLException {
        String  query1 = "Select * from savingstable";
        PreparedStatement query = conObj.prepareStatement(query1);

        //11. Getting the result fo the data
        ResultSet rs = query.executeQuery();

        //12. Set a table (df) with the name of the table (table1) in the form
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        //13. Traverse the table, and we will need a bi dimensional array for that
        //First the row count to go to the end
        rs.last();
        int z = rs.getRow();

        //put it back to its original place
        rs.beforeFirst();

        //14. Creating the array called array
        String[][] array = new String[list1.size()][5];

        //15. See if there is still one row or not. Rows > 0
        if(z > 0) {
            //The number of rows existing and 2 columnos fixed (as we set it in the table from mySQL
            array = new String[z][2];
        }

        //16. Traverse putting the data from the database to the array
        int j = 0;
        //17. Creating a two-dimensional array inputting the data from each row
        // We are using getString cuz it is a String array as we defined before, if it was a double should be getDouble
        while (rs.next()) {

            for (int i = 0; i < list1.size(); ++i) {

                array[i][0] = list1.get(i).getCusNum();
                array[i][1] = list1.get(i).getCusName();
                array[i][2] = list1.get(i).getInitialDeposit();
                array[i][3] = list1.get(i).getNumYears();
                array[i][4] = list1.get(i).getTypeSavings();
                ++j;
            }

        }

        //18. Creating column headers for our table
        String[] cols = {"Number", "Name", "Deposit", "Years", "Type of Savings"};

        //19. Creating the table
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);




    }

    public MySavings() throws SQLException, ClassNotFoundException {
        initComponents();
    }

    private void btnAddMouseClicked(MouseEvent e) throws SQLException {
        String num = txtcusNum.getText();
        String name = txtcusName.getText();
        String deposit = txtinitDepo.getText();
        String years = txtnumYears.getText();
        String savings = "";

        //NEED TO HANDLE HERE THE COMBO BUTTON

        list1.add(new Savings(num, name, deposit, years, savings));

        String[][] array = new String[list1.size()][5];

        for (int i = 0; i < list1.size(); ++i) {

            //Transfer the data from the list to the hashmap (dimensional array)
            array[i][0] = list1.get(i).getCusNum();
            array[i][1] = list1.get(i).getCusName();
            array[i][2] = list1.get(i).getInitialDeposit();
            array[i][3] = list1.get(i).getNumYears();
            array[i][4] = list1.get(i).getTypeSavings();

        }
        String[] cols = {"Number", "Name", "Deposit", "Years", "Type of Savings"};
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);

        String query1 = "Select * from savingstable where custno =?";
        PreparedStatement query = conObj.prepareStatement(query1);

        query.setString(1, num);

        ResultSet rs = query.executeQuery();

        if(rs.isBeforeFirst()) {
            JOptionPane.showMessageDialog(null, "This record exists already");

            //WE NEED TO SET THE BOXES TO EMPTY SPACE HERE
            return;
        }

        //25. Data is validated as new. We create insert query
        String query2 = "Insert into category values (?,?)";
        query = conObj.prepareStatement(query2);

        //26. Filling parameters
        query.setString(1, num);
        query.setString(2, num);

        //27. Updating query
        query.executeUpdate();
        JOptionPane.showMessageDialog(null, "One record added");
        updateTable();

    }

    private void table1MouseClicked(MouseEvent e) {
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        txtcusNum.setText(df.getValueAt(index, 0).toString());
        txtcusName.setText(df.getValueAt(index, 1).toString());
        txtinitDepo.setText(df.getValueAt(index, 2).toString());
        txtnumYears.setText(df.getValueAt(index, 3).toString());
        //cmbtypeSavings.setText(df.getValueAt(index, 1).toString());


    }

    private void btnEditMouseClicked(MouseEvent e) throws SQLException {
        //Need to get ge old value from the table
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        String num = txtcusNum.getText();
        String name = txtcusName.getText();
        String deposit = txtinitDepo.getText();
        String years = txtnumYears.getText();
        String savings = "";


        //NEED TO HANDLE HERE THE COMBO BUTTON

        list1.add(new Savings(num, name, deposit, years, savings));

        String[][] array = new String[list1.size()][5];

        for (int i = 0; i < list1.size(); ++i) {

            //Transfer the data from the list to the hashmap (dimensional array)
            array[i][0] = list1.get(i).getCusNum();
            array[i][1] = list1.get(i).getCusName();
            array[i][2] = list1.get(i).getInitialDeposit();
            array[i][3] = list1.get(i).getNumYears();
            array[i][4] = list1.get(i).getTypeSavings();

        }
        String[] cols = {"Number", "Name", "Deposit", "Years", "Type of Savings"};
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);

        String oldvalue = df.getValueAt(index, 0).toString();

        String query = "Update savingstable set custnom=?,cusname=?,cdep=?,nyears=?,savtype=? where custno=?";
        PreparedStatement query2 = conObj.prepareStatement(query);

        query2.setString(1, num);
        query2.setString(2, name);
        query2.setString(3, deposit);
        query2.setString(4, years);
        query2.setString(5, savings);
        query2.setString(6, oldvalue);

        query2.executeUpdate();

        updateTable();



    }

    private void btnDeleteMouseClicked(MouseEvent e) throws SQLException {
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        String num = txtcusNum.getText();
        String name = txtcusName.getText();
        String deposit = txtinitDepo.getText();
        String years = txtnumYears.getText();
        String savings = "";


        //NEED TO HANDLE HERE THE COMBO BUTTON

        list1.add(new Savings(num, name, deposit, years, savings));

        String[][] array = new String[list1.size()][5];

        for (int i = 0; i < list1.size(); ++i) {

            //Transfer the data from the list to the hashmap (dimensional array)
            array[i][0] = list1.get(i).getCusNum();
            array[i][1] = list1.get(i).getCusName();
            array[i][2] = list1.get(i).getInitialDeposit();
            array[i][3] = list1.get(i).getNumYears();
            array[i][4] = list1.get(i).getTypeSavings();

        }
        String[] cols = {"Number", "Name", "Deposit", "Years", "Type of Savings"};
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);

        String query = "Delete from savingstable where custnom =?";
        PreparedStatement query2 = conObj.prepareStatement(query);

        query2.setString(1, num);


        query2.executeUpdate();

        updateTable();


    }











    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        lbcusNum = new JLabel();
        txtcusNum = new JTextField();
        lbcusName = new JLabel();
        txtcusName = new JTextField();
        lbInitDepo = new JLabel();
        txtinitDepo = new JTextField();
        lbnumYears = new JLabel();
        txtnumYears = new JTextField();
        lbtypeSavings = new JLabel();
        cmbtypeSavings = new JComboBox();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- lbcusNum ----
        lbcusNum.setText("Enter the Customer Number");
        contentPane.add(lbcusNum, "cell 0 0");

        //---- txtcusNum ----
        txtcusNum.setColumns(30);
        contentPane.add(txtcusNum, "cell 2 0");

        //---- lbcusName ----
        lbcusName.setText("Enter the Customer Name");
        contentPane.add(lbcusName, "cell 0 1");
        contentPane.add(txtcusName, "cell 2 1");

        //---- lbInitDepo ----
        lbInitDepo.setText("Enter the Initial Deposit");
        contentPane.add(lbInitDepo, "cell 0 2");
        contentPane.add(txtinitDepo, "cell 2 2");

        //---- lbnumYears ----
        lbnumYears.setText("Enter the Number of Years");
        contentPane.add(lbnumYears, "cell 0 3");
        contentPane.add(txtnumYears, "cell 2 3");

        //---- lbtypeSavings ----
        lbtypeSavings.setText("Choose the Type of Savings");
        contentPane.add(lbtypeSavings, "cell 0 4");

        //---- cmbtypeSavings ----
      /*  cmbtypeSavings.setSelectedIndex(0);
        contentPane.add(cmbtypeSavings, "cell 2 4");

       */

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }
        contentPane.add(scrollPane2, "cell 2 5");

        //---- btnAdd ----
        btnAdd.setText("Add");
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    btnAddMouseClicked(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btnAdd, "cell 0 6");

        //---- btnEdit ----
        btnEdit.setText("Edit");
        btnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    btnEditMouseClicked(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btnEdit, "cell 0 6");

        //---- btnDelete ----
        btnDelete.setText("Delete");
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    btnDeleteMouseClicked(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btnDelete, "cell 0 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel lbcusNum;
    private JTextField txtcusNum;
    private JLabel lbcusName;
    private JTextField txtcusName;
    private JLabel lbInitDepo;
    private JTextField txtinitDepo;
    private JLabel lbnumYears;
    private JTextField txtnumYears;
    private JLabel lbtypeSavings;
    private JComboBox cmbtypeSavings;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
