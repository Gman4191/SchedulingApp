Title: Auto-Department Appointment Scheduler
Author: Gavin Pulley
Email: gpulley@wgu.edu
Date: 1/10/2023
---------------------------------------------------------------------------------------------
|                                                                                           |
|     The purpose of this application is to demonstrate the use of JavaFX GUI,Lambda        |
| functions, and a mySQL database server. It provides the user with the ability to          |
| manage customer information, schedule vehicle servicing appointments for those customers, |
| and create a few different report examples based on the managed information.              |
|                                                                                           |
---------------------------------------------------------------------------------------------

Application Version 1.1

IDE Version:          IntelliJ Community Edition 2021.1.3 x64
Java Version:         Java SE 11.0.11
JavaFX Version:       JavaFX-SDK-11.0.2
mySQL Driver Version: mysql-connector-java:8.0.25

How to run the program:
    Launch the application
    Login with a valid user name and password. Entering "admin" or "test" in both fields will suffice.
    A notification will tell you if there are any upcoming appointments, select OK.
    Three tabs will be visible on the main menu. Navigate to the desired tab.
        The Customer tab will allow you add, modify, and delete customer information.
        To add a new customer, select the "Add" button to go to the add form.
        To modify a customer, select the customer to modify in the table and select the "Modify" button to go to the modify form.
        When adding a customer, fill out every field and select "Save" when finished.
        When modifying a customer, change the desired information, and select "Save" when finished.
        Select "Cancel" to return to the main menu without saving any changes.

        The Appointment tab will allow you to add, modify, and delete appointments.
        To add an appointment, select "Add" to go to the add form.
        To modify an appointment, select the appointment to modify in the table and select "Modify" to go to the modify form.
        When adding an appointment, fill out every field and select "Save" when finished.
        When modifying an appointment, change the desired information and select "Save" when finished.
        Select "Cancel" to return to the Appointments tab in the main menu without saving any changes.

        The Reports tab will allow you to get the following information:
            1. The total number of appointments given the type of the appointment and desired month to filter by.
            2. The appointment information associated with a given contact.
            3. The appointment information associated with a given customer.
        To get the information of the first option, select the "Monthly Appointment Totals" tab.
            Select the desired appointment type and month to count. The total count will update automatically.
        To get the information of the second option, select the "Contact Schedule" tab.
            Select the name of the desired contact. The table of appointment information will update automatically.
        To get the information of the third option, select the "Customer Schedule" tab.
            Select the name of the desired customer. The table of appointment information will update automatically.

    The additional report that was added in section A3F displays appointment information for a given customer.
This information is used in real-world applications to verify the type of service a customer is going to receive,
the time of their appointment, and the user responsible for creating the appointment along with other information.

