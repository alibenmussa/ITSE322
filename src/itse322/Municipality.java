package itse322;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * مشروع مادة جافا المتقدمة
 * 
 * 216180392 - يوسف عبد الكريم بريكة
 * 216180296 - علي جمال الدين بن موسى
 * 
 */


public class Municipality extends JFrame {
    
    public DefaultTableModel model;
    
    public Municipality() {
        initComponents();
        //تغيير لون الخلفية
        getContentPane().setBackground(new Color(248, 248, 251));
        //عرض بيانات البلديات في الجدول
        showData(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Municipality");
    }
    
    public ArrayList<Muni> getMuniList(String search){
        ArrayList<Muni> muniList = new ArrayList<>();
        try{
            Connection connection = DB.DBconnect();
            String query;
            
            //إذا كانت قيمة هذا المتغير غير معرفة فهنا يتم جلب جميع البيانات
            //أما إذا كانت لديه قيمة فإنه يتم جلب البلديات التي قيمتها تبدأ بقيمة مثلx
            if (search == null)
                query = "SELECT * FROM `muni` ORDER BY `id` ASC";
            else {
                //البحث عن البلدية إما ببداية اسمها أو ببداية رقمه
                query = "SELECT * FROM `muni` WHERE `municipality_name` LIKE '" + search + "%' OR `id` LIKE '" + search + "%' ORDER BY `id` ASC";
            }
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while(rs.next()){
                Muni m = new Muni();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("municipality_name"));
                m.setGeography(rs.getString("geography"));
                m.setPop(rs.getInt("population"));
                m.setNod(rs.getString("Dean_of_the_Muni"));
                m.setPhone_number(rs.getInt("phone_number"));
                muniList.add(m);
            }
            rs.close();
            stmt.close();
            connection.close();
            
        } catch(Exception ex){
            Alert.viewWarningMessage("حدث خطأ! يرجى إعادة المحاولة");
        } finally {
            return muniList;
        }
    }
    
    public void showData(String search){
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        //إنشاء مصفوفة من نوعMuni لتخزين البيانات المجلوبة من قاعدة البيانات فيها
        ArrayList<Muni> list = getMuniList(search);
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        int selecteddRow = table.getSelectedRow();

        //تفعيل أزرار الحذف والتعديل عندما يتم اختيار أحد الصفوف
        //وتعطيلها عندما لا يكون هناك أي بلدية في الجدول أو عند عدم اختيار صف
        if (list.size() > 0 && selecteddRow != -1) {
            btn_delete.setEnabled(true);
            btn_edit.setEnabled(true);
        } else {
            btn_delete.setEnabled(false);
            btn_edit.setEnabled(false);
        }
        
        Object[] row = new Object[6];
        //إضافة البيانات التي تم جلبها من قاعدة اليانات للجدول
        for(int i = 0 ; i < list.size() ; i++){
            row[0] = list.get(i).getPhone_number();
            row[1] = list.get(i).getNod();
            row[2] = list.get(i).getPop();
            row[3] = list.get(i).getGeography();
            row[4] = list.get(i).getName();
            row[5] = list.get(i).getId();
            model.addRow(row);
        }
    }
    
    //دالة لتنفيذ الكويريز مع إظهار رسالة نجاح عند نجاح التنفيذ ورسالة خطأ عند الفشل
    //قمنا بإنشاء هذه الدالةلاستدعاءها  بدلا من نسخ كود تنفيذ أوامر قاعدة البيانات في كل مرة
    public void executeSQLQuery(String  query, String message, String error)
    {
        //إذا لم يمرر للدالة رسالة خطأ معينة يتم عرض هذه الرسالة
        if (error == null) {
          error = "حدث خطأ غير متوقع، يرجى إعادة المحاولة";
        }
        try
        {
            Connection connection = DB.DBconnect();
            Statement stmt = connection.createStatement();
            if((stmt.executeUpdate(query)) == 1)
            {
                //تحديث البيانات في الجدول عند نجاح العملية
               showData(null);
               clearFields();
               Alert.viewSuccessMessage(message);
            }
            else{
                Alert.viewErrorMessage(error);
            }
        }  catch (SQLException ex) {
               Alert.viewErrorMessage(error);
        }
    }
    
    //تفريغ قيم حقول البيناتات ومربع البحث
    public void clearFields() {
        search_text.setText("");
        id_text.setText("");
        name_text.setText("");
        pop_text.setText("");
        nod_text.setText("");
        phoneNumber_text.setText("");
    }
    
    //التحقيق من الحقول وأنها ليست فارغة والتأكد من الحقول التي يجب أن تكون قيمتها أرقام
    public boolean checkFields() {
        if (id_text.getText().isEmpty() || name_text.getText().isEmpty() || pop_text.getText().isEmpty() || nod_text.getText().isEmpty() || phoneNumber_text.getText().isEmpty()) {
            return false;
        }
        try {
            if (Integer.parseInt(id_text.getText()) <= 0 || Integer.parseInt(pop_text.getText()) <= 0 || Integer.parseInt(phoneNumber_text.getText()) <= 0) {
                return false;
            }
        } catch(Exception ex) {
            return false;
        }
        return true;
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        usernameField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        id_text = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        name_text = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        geography_combo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        pop_text = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nod_text = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        phoneNumber_text = new javax.swing.JTextField();
        btn_delete = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        search_text = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("ID :");

        usernameField6.setBackground(new java.awt.Color(60, 63, 66));
        usernameField6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        usernameField6.setForeground(new java.awt.Color(204, 204, 204));
        usernameField6.setBorder(null);
        usernameField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameField6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                usernameField6MouseEntered(evt);
            }
        });
        usernameField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameField6ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("رقـــم الـبـلــديـــة");

        id_text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        id_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_textActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("اســـم الـبـلــديـــة");

        name_text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        name_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_textActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("المنطقة الجغرافية");

        geography_combo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        geography_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "طرابلس الكبرى", "سهل جفارة", "وادي الحياة", "الجبل الأخضر", "البطنان", "وادي الشاطئ", "الجبل الغربي", "غات", " " }));
        geography_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geography_comboActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("عــدد الــســكــان");

        pop_text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pop_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop_textActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("اســـم الـعــمــيــد");

        nod_text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nod_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nod_textActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("رقـــم الـهــاتـــف");

        phoneNumber_text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        phoneNumber_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumber_textActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(204, 0, 51));
        btn_delete.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("حــــــذف");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("وزارة الـــحـــكـــم الـــمـــحـــلـــي - نـــظـــام بـــلـــديـــات لـــيــــبــــيـــا");

        search_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        search_text.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        search_text.setToolTipText("ادخل اسم أو رقم البلدية");
        search_text.setName(""); // NOI18N
        search_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textActionPerformed(evt);
            }
        });
        search_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_textKeyReleased(evt);
            }
        });

        table.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "رقم الهاتف", "اسم العميد", "عدد السكان", "المنطقة الجغرافية", "اسم البلدية", "رقم البلدية"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFocusable(false);
        table.setRequestFocusEnabled(false);
        table.setRowHeight(30);
        table.setRowMargin(0);
        table.setSelectionBackground(new java.awt.Color(0, 153, 153));
        table.getTableHeader().setResizingAllowed(false);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        for(int i = 0; i < 6; i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(2).setPreferredWidth(50);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(5).setPreferredWidth(25);
        }

        btn_add.setBackground(new java.awt.Color(0, 153, 102));
        btn_add.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("إضـــافـــة");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(0, 102, 204));
        btn_edit.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("تـــعـــديـــل");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("الـبـحـث عـن بـلـديـة");

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("إضـافـة وتـعـديـل البـيـانـات");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(search_text)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(name_text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(geography_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pop_text, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nod_text, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phoneNumber_text, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(id_text, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(50, 50, 50))
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_text, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_text))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(name_text))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(geography_combo))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pop_text))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nod_text))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneNumber_text, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameField6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameField6MouseClicked

    private void usernameField6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameField6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameField6MouseEntered

    private void usernameField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameField6ActionPerformed

    private void id_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_textActionPerformed

    private void geography_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geography_comboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_geography_comboActionPerformed

    //دالة حذف بيانات بلدية من الجدول
    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int selectedRow = table.getSelectedRow();
        //التأكد من أن هناك صف تم تحديده في الجدول
        if (selectedRow != -1) {
        String query = "DELETE FROM `muni` WHERE id =  " + table.getModel().getValueAt(selectedRow, 5).toString();
        executeSQLQuery(query, "تم حذف هذه البلدية بنجاح!", null);
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void search_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textActionPerformed
    
    }//GEN-LAST:event_search_textActionPerformed

    //دالة إضافة بلدية
    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        if (checkFields() == true) {
            String query = "INSERT INTO `muni` VALUES (" + id_text.getText() + ", '" + name_text.getText() + "', '" + geography_combo.getSelectedItem().toString() + "', "  + pop_text.getText() + ", '" + nod_text.getText() + "', " + phoneNumber_text.getText() + ")";
            String message = "تمت إضافة " + name_text.getText() + " بنجاح";
            String error = "رقم البلدية الذي ادخلته موجود مسبقا";
            executeSQLQuery(query, message, error);
        } else {
            Alert.viewErrorMessage("قمت بإدخال غير صحيح");
        }
    }//GEN-LAST:event_addActionPerformed

    //دالة تحديث بيانات بلدية ما
    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        try {
            Connection connection = DB.DBconnect();
            String query = "UPDATE `muni` SET `municipality_name` = ?, `geography` = ?, `population`= ?, `Dean_of_the_Muni`= ?, `phone_number`= ? WHERE `id` = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            
             ps.setString(1, name_text.getText());
             ps.setString(2, geography_combo.getSelectedItem().toString());
             ps.setString(3, pop_text.getText());
             ps.setString(4, nod_text.getText());
             ps.setString(5, phoneNumber_text.getText());
             ps.setString(6, id_text.getText());
             
             if (ps.executeUpdate() == 1) {
                Alert.viewSuccessMessage("تم تحديث بيانات البلدية بنجاح");
                showData(null);
                clearFields();
             } else {
                 Alert.viewErrorMessage("قمت بإدخال غير صحيح");
             }
             ps.close();
             connection.close();
        } catch(SQLException ex) {
            Alert.viewErrorMessage("قمت بإدخال غير صحيح");
        }
    }//GEN-LAST:event_editActionPerformed

    private void name_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_textActionPerformed

    private void pop_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pop_textActionPerformed

    private void nod_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nod_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nod_textActionPerformed

    private void phoneNumber_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumber_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumber_textActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            //تفعيل أزرار الحذف والتعديل عند اختيار الصف
            btn_delete.setEnabled(true);
            btn_edit.setEnabled(true);
            
            //عرض الصف الذي تم اختيار من الجدول في الحقول الجانبية
            TableModel model = table.getModel();
            id_text.setText(model.getValueAt(selectedRow, 5).toString());
            name_text.setText(model.getValueAt(selectedRow, 4).toString());
            geography_combo.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
            pop_text.setText(model.getValueAt(selectedRow, 2).toString());
            nod_text.setText(model.getValueAt(selectedRow, 1).toString());
            phoneNumber_text.setText(model.getValueAt(selectedRow, 0).toString());
        }
        
    }//GEN-LAST:event_tableMouseClicked

    //بمجرد كتابة حرف تتم عملية البحث
    private void search_textKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textKeyReleased
        String search = search_text.getText();
        //جلب بيانات البلديات في الجدول بناء على الكلمة المدخلة في حقل البحث
        showData(search);
    }//GEN-LAST:event_search_textKeyReleased

    //الدالة الرئيسية
    public static void main(String args[]) {
       /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Municipality.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Municipality.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Municipality.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Municipality.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Municipality().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JComboBox<String> geography_combo;
    private javax.swing.JTextField id_text;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name_text;
    private javax.swing.JTextField nod_text;
    private javax.swing.JTextField phoneNumber_text;
    private javax.swing.JTextField pop_text;
    private javax.swing.JTextField search_text;
    private javax.swing.JTable table;
    private javax.swing.JTextField usernameField6;
    // End of variables declaration//GEN-END:variables
}
