Multiple SMS Analyzer was created for report generation purpose. I had to submit a weekly report on SMS sending through a portal. After generating report for 2-3 weeks I got bored and wrote this program which will generate the report files on a particular time. I schedule the event without using thread so it was memory efficient and made my life easier. :D

How to configure the application:
1. Open the multipleSms-log4jConfig.xml file resides in conf directory.
2. Change the value in appender1 from "C:/JavaPrograms/MultipleSmsAnalyser/logs/SMSCountlog_" to the location of the logs folder in your system. If you use linux/mac/windows then find the location of "logs" path using "pwd" command and replace the location of logs folder in appender1. SMSCountlog_ is the file name the application will generate. 
3. Open the input.xml file and changed value of the output location, query and database credentials accordingly. 
4. I used cron4j for schedule my application to run in a specific time. change the schedule file accordingly. To save time I put cronfj description in the end of this file.
5. Change the path of input.xml file in the schedule file based on the location in your system. 
4. Run the application. 


Cron4j:
cron4j is a scheduler for the Java platform which is very similar to the UNIX cron daemon. With cron4j you can launch, from within your Java applications, any task you need at the right time, according to some simple rules.

The Java 2 platform already has a built-in scheduler, implemented with the class java.util.Timer. The cron4j scheduler, however, acts in a different way. You can say to the java.util.Timer scheduler something like "launch this task after 5 minutes from now" or "launch it after 5 minutes from now, then repeat it every 10 minutes". That's all. The cron4j scheduler, instead, lets you do something a little more complex, like "launch this task every Monday, at 12:00", "launch it every 5 minutes, but don't launch it during the weekend", "launch it every hour between the 8:00AM and the 8:00PM and launch it every 5 minutes between the 8:00PM and the 8:00AM", "launch it once every day but Sunday, during every month but July and August" and so on, and all that with a single line of code.

Programming the cron4j scheduler is easy, you have to know just a few methods. The launching rules for every task must be expressed with a string called scheduling pattern, whose syntax is equal to the one used by the UNIX crontab entries. If you know how to manipulate the UNIX crontab, the most is done. If you can't, don't worry: crontab scheduling patterns rules are very simple and you can learn them in minutes. By the way, you will find them explained in the cron4j documentation.


Some examples:

5 * * * *
This pattern causes a task to be launched once every hour, at the begin of the fifth minute (00:05, 01:05, 02:05 etc.).

* * * * *
This pattern causes a task to be launched every minute.

* 12 * * Mon
This pattern causes a task to be launched every minute during the 12th hour of Monday.

* 12 16 * Mon
This pattern causes a task to be launched every minute during the 12th hour of Monday, 16th, but only if the day is the 16th of the month.

Every sub-pattern can contain two or more comma separated values.

59 11 * * 1,2,3,4,5
This pattern causes a task to be launched at 11:59AM on Monday, Tuesday, Wednesday, Thursday and Friday.

Values intervals are admitted and defined using the minus character.

59 11 * * 1-5
This pattern is equivalent to the previous one.

The slash character can be used to identify step values within a range. It can be used both in the form */c and a-b/c. The subpattern is matched every c values of the range 0,maxvalue or a-b.

*/5 * * * *
This pattern causes a task to be launched every 5 minutes (0:00, 0:05, 0:10, 0:15 and so on).

3-18/5 * * * *
This pattern causes a task to be launched every 5 minutes starting from the third minute of the hour, up to the 18th (0:03, 0:08, 0:13, 0:18, 1:03, 1:08 and so on).

*/15 9-17 * * *
This pattern causes a task to be launched every 15 minutes between the 9th and 17th hour of the day (9:00, 9:15, 9:30, 9:45 and so on... note that the last execution will be at 17:45).

All the fresh described syntax rules can be used together.

* 12 10-16/2 * *
This pattern causes a task to be launched every minute during the 12th hour of the day, but only if the day is the 10th, the 12th, the 14th or the 16th of the month.

* 12 1-15,17,20-25 * *
This pattern causes a task to be launched every minute during the 12th hour of the day, but the day of the month must be between the 1st and the 15th, the 20th and the 25, or at least it must be the 17th.

Finally cron4j lets you combine more scheduling patterns into one, with the pipe character:

0 5 * * *|8 10 * * *|22 17 * * *
This pattern causes a task to be launched every day at 05:00, 10:08 and 17:22.