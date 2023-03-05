# Learning_progress_tracker

Your Learning Progress Tracker is fine, but it is missing a few important features. We need to know which students have completed which courses and send the acknowledgments of their success, as well as personal certificates to them. Remember we asked students to specify their full names and email addresses? Now it's time to use this personal information to contact the top students.


Example of usage:
Learning Progress Tracker
> statistics
Type the name of a course to see details or 'back' to quit:
Most popular: n/a
Least popular: n/a
Highest activity: n/a
Lowest activity: n/a
Easiest course: n/a
Hardest course: n/a
> java
Java
id	points	completed
> swing
Unknown course.
> back
> exit
Bye!

Learning Progress Tracker
> add students
Enter student credentials or 'back' to return:
> John Doe johnd@email.net
The student has been added.
> Jane Spark jspark@yahoo.com
The student has been added.
> back
Total 2 students have been added.
> list
Students:
10000
10001
> add points
Enter an id and points or 'back' to return:
> 10000 600 400 0 0
Points updated.
> back
> notify
To: johnd@email.net
Re: Your Learning Progress
Hello, John Doe! You have accomplished our Java course!
To: johnd@email.net
Re: Your Learning Progress
Hello, John Doe! You have accomplished our DSA course!
Total 1 students have been notified.
> notify
Total 0 students have been notified.
> exit
Bye!
