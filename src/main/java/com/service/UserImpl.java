package com.service;

import com.resource.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserImpl {

    public String save(User user)
    {
        String name= user.getName();
        String email=user.getEmail();
        Connection connection=null;
        PreparedStatement statement=null;
        int res=0;
        int updateFlag=0;
        String message="";
        try {
             connection=getDataBaseConnection();
                String query="insert  into user(name,email) values(?,?)";
                statement=getPreparedStatement(query,connection);
                res = insertQuery(statement,name,email);
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            e.printStackTrace();
            message=e.getLocalizedMessage();
            String query="update user set name=? where email=?";
            try {
                statement=getPreparedStatement(query,connection);
                if(!(email.isEmpty()&&name.isEmpty())){
                    res = insertQuery(statement,name,email);
                }
                else
                    message=message+"\t"+"name and email can't be empty";
                if(res!=0) {
                    message=message+"\t"+"data Record Updated Successfully";
                    updateFlag=1;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally {
            close(connection);
        }
        if (res!=0 && updateFlag==0)
            message="Record inserted in database successfully";
        return message;
    }
    public List<User> retrieve(int id){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<User> users=null;
        try {
            connection = getDataBaseConnection();
            String query="select * from user where id=?";
            statement=getPreparedStatement(query,connection);
            resultSet=processSelectQuery(statement,id);
            users=getAllUser(resultSet);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(connection);
        }
        return users;
    }
    public String delete(int id,String email){
        Connection connection=null;
        PreparedStatement statement=null;
        String message=null;
        int res=0;
        try {
            connection=getDataBaseConnection();
            String query="delete from user where id=? and email=?";
            statement=getPreparedStatement(query,connection);
            res=processDeleteQuery(statement,id,email);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res!=0) {
            message="User deleted Successfully";
        }
        else {
            message="User Not found";
        }
        return message;
    }
    public int processDeleteQuery(PreparedStatement preparedStatement,int id,String email) throws SQLException {
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,email.trim());
        return preparedStatement.executeUpdate();
    }
    public List<User> getAllUser(ResultSet resultSet) throws SQLException {
        List<User> users=new ArrayList<>();
        while (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            users.add(user);
        }
        return users;
    }
    public PreparedStatement getPreparedStatement(String query,Connection connection) throws SQLException {
       return connection.prepareStatement(query);
    }
    public ResultSet processSelectQuery(PreparedStatement preparedStatement,int id) throws SQLException {
        preparedStatement.setInt(1,id);
        return  preparedStatement.executeQuery();
    }
    public Connection getDataBaseConnection() throws ClassNotFoundException, SQLException {
        Connection connection;
        Class.forName("com.mysql.cj.jdbc.Driver");
        //connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Raj?autoReconnect=true&useSSL=false","root","Anjali12");
        connection= DriverManager.getConnection("jdbc:mysql://db:3306/Raj?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=CTT","root","Anjali12");
        return connection;
    }
    public void close(Connection connection)
    {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int insertQuery(PreparedStatement statement,String name,String email) throws SQLException {
        statement.setString(1,name.trim());
        statement.setString(2,email.trim());
        return statement.executeUpdate();
    }
}
