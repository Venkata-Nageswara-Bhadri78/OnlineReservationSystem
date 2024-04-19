# OnlineReservationSystem

Technologies Used : Core Java, MySQL, Java DataBase Connection (JDBC)

For a customer visiting for the first time he needs to register by providing relevent information asked.

Then the system generates a unique PNR Number consisting 12 digits for the user.

For the second time the user needs to login and then enter the train Number then the train with perticular train details are displayed along with journey timings and entire essential information.

If the user wants to book ticket then he needs to enter the text - "Yes" otherwiser "No".

then the ticket will be booked sucessfully.

If the use wants to cancel the tickets then then choose the option cancel Tickets.

in it the customer needs to enter his unique PNR Number then the details are displayed clearly belonging to his/her journey.

then if he want to cancel enter the text 'Yes' then the user deatails will be deleted completly from the database.

tables used :

//LogIn Details Table
| Column Name   | Data Type      | Nullable | Primary Key |
|---------------|----------------|----------|-------------|
| UserName      | varchar(255)   | NO       | YES         |
| MobileNumber  | varchar(10)    | YES      |             |
| Password      | varchar(255)   | YES      |             |
| PNRNumber     | varchar(12)    | YES      |             |
| FullName      | varchar(255)   | YES      |             |
| Booking       | int            | YES      |             |

 //Train Deatils table and list

 | Column Name      | Data Type     | Nullable | Primary Key |
|------------------|---------------|----------|-------------|
| trainNumber      | int           | NO       | YES         |
| trainName        | varchar(255)  | YES      |             |
| start            | varchar(255)  | YES      |             |
| destination      | varchar(255)  | YES      |             |
| dateOfJourney    | varchar(255)  | YES      |             |
| classType        | varchar(255)  | YES      |             |


------------------------------------------------------------------------------------
| Booking | Train Name            | Source   | Destination | Date       | Class    |
|---------|-----------------------|----------|-------------|------------|----------|
| 12345   | Venkatabri Express    | Chennai  | Mumbai      | 20/04/2024 | Sleeper  |
| 12346   | Shamshabad Express    | Chennai  | Hyderabad   | 21/04/2024 | General  |
| 12347   | Deccan Express        | Tirupati | Vijayawada  | 22/04/2024 | AC       |
| 12348   | Vandebharat Express   | Vijayawada | Nellore  | 26/04/2024 | Sleeper  |
| 12349   | Krishna Express       | Krishna  | Delhi       | 22/04/2024 | General  |
