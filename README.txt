# Auto-Department Appointment Scheduler

**Author:** Gavin Pulley  
**Email:** gpulley@wgu.edu  
**Date:** 1/10/2023

---

## Overview

The Auto-Department Appointment Scheduler is a Java application that demonstrates the use of JavaFX GUI, Lambda functions, and a MySQL database server. This application provides users with the ability to manage customer information, schedule vehicle servicing appointments for those customers, and generate various reports based on the managed information.

**Application Version:** 1.1

### Development Environment

- **IDE Version:** IntelliJ Community Edition 2021.1.3 x64
- **Java Version:** Java SE 11.0.11
- **JavaFX Version:** JavaFX-SDK-11.0.2
- **MySQL Driver Version:** mysql-connector-java:8.0.25

---

## How to Run the Program

1. Launch the application.
2. Login with a valid username and password. You can use "admin" or "test" in both fields for testing purposes.
3. A notification will inform you of any upcoming appointments. Click "OK" to proceed.
4. The main menu will display three tabs. Navigate to the desired tab:

   - **Customer Tab:** Allows you to add, modify, and delete customer information.
     - To add a new customer, click the "Add" button to access the add form.
     - To modify a customer, select the customer from the table and click the "Modify" button to access the modify form.
     - When adding a customer, fill out every field and click "Save" when finished.
     - When modifying a customer, make the desired changes and click "Save" when finished. Click "Cancel" to return to the main menu without saving changes.

   - **Appointment Tab:** Allows you to add, modify, and delete appointments.
     - To add an appointment, click "Add" to access the add form.
     - To modify an appointment, select the appointment from the table and click "Modify" to access the modify form.
     - When adding an appointment, complete all fields and click "Save" when finished.
     - When modifying an appointment, update the desired information and click "Save" when finished. Click "Cancel" to return to the Appointments tab without saving changes.

   - **Reports Tab:** Provides the following information:
     1. Total number of appointments based on appointment type and a specified month filter.
     2. Appointment information associated with a specific contact.
     3. Appointment information associated with a specific customer.
     - To get information for the first option, select the "Monthly Appointment Totals" tab. Choose the appointment type and month to count; the total count will update automatically.
     - To get information for the second option, select the "Contact Schedule" tab. Select the name of the desired contact; the table of appointment information will update automatically.
     - To get information for the third option, select the "Customer Schedule" tab. Choose the name of the desired customer; the table of appointment information will update automatically.

   - **Additional Report:** Displays appointment information for a given customer. This information is valuable in real-world applications to verify the type of service a customer is scheduled to receive, appointment time, the user responsible for creating the appointment, and other relevant details.

---

Feel free to reach out to the author, Gavin Pulley, at [gpulley@wgu.edu](mailto:gpulley@wgu.edu) for any inquiries or support regarding this application.

**Thank you for using the Auto-Department Appointment Scheduler!**
