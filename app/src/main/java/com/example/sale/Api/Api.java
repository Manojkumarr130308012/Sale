package com.example.sale.Api;

public class Api {

    public static final String Mainurl="https://invoice.zopay.in/API/";
    public static final String secMainurl="https://invoice.zopay.in/api/";

    public static final String Loginurl=Mainurl+"Users.php?f=UserLogin";

    public static final String Dashboardurl=Mainurl+"Dashboard.php?f=Dashboard";

    public static final String ticketurl=secMainurl+"Ticket.php?f=GetTickets";
    public static final String getitem=secMainurl+"Items.php?f=GetItems";
    public static final String ticketcrt=secMainurl+"Items.php?f=GetItems";

    public static final String Customerlisturl=Mainurl+"Customers.php?f=GetAllCustomers";

    public static final String Allitemlisturl=Mainurl+"Items.php?f=GetAllItems";

    public static final String gettax=Mainurl+"Taxes.php?f=GetTaxes";

    public static final String getunit=Mainurl+"Units.php?f=GetUnits";

    public static final String additem=Mainurl+"Items.php?f=CreateItem";
}
