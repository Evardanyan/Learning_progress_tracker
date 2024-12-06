from dataclasses import dataclass
import re

@dataclass
class LearningProgressTracker:
    greeting: str = 'Learning progress tracker'
    student_id: int = 10000
    students_list: dict = None
    commands_list: dict = None
    courses_list: dict = None
    points_list: dict = None

    def __post_init__(self):
        if self.commands_list is None:
            self.commands_list = self.default_commands()
        if self.students_list is None:
            self.students_list = {}
        if self.courses_list is None:
            self.courses_list = {
                'DSA': [],
                'Databases': [],
                'Flask': [],
                'Python': []
            }
        if self.points_list is None:
            self.points_list = {
                'Python': 600,
                'DSA': 400,
                'Databases': 480,
                'Flask': 550
            }

        # Precompile regex for performance
        self.name_pattern = re.compile(r"^(?!['-])\b[A-z](?:['-]?[A-z])+([a-z])*\b$")
        self.last_name_pattern = re.compile(r"^((?:\s*)(?!['-])\b[A-z](?:['-]?[A-z])+([a-z])*\b)*$")
        self.email_pattern = re.compile(r"([A-Za-z0-9._%+\-]+@[A-Za-z0-9.\-]+\.[A-Za-z0-9]{1,})")

    def default_commands(self):
        return {
            'exit': self.handle_exit,
            'add students': self.handle_add_students,
            'back': self.handle_back,
            'list': self.handle_list,
            'add points': self.handle_add_points,
            'find': self.handle_find,
            'statistics': self.handle_statistics,
            'notify': self.handle_notify
        }

    def print_greeting(self):
        print(self.greeting)

    def get_student_email_by_id(self, s_id):
        for key, val in self.students_list.items():
            if val['student_id'] == s_id:
                return key
        return None

    def check_input_string(self, input_string):
        parts = input_string.split()
        if len(parts) < 3:
            print('Incorrect credentials.')
            return False

        name = parts[0]
        email = parts[-1]
        last_name = ' '.join(parts[1:-1])

        if not name or not last_name or not email:
            print('Incorrect credentials.')
            return False

        name_match = self.name_pattern.match(name)
        last_name_match = self.last_name_pattern.match(last_name)
        email_match = self.email_pattern.match(email)

        if not (name_match and len(name) > 1):
            print('Incorrect first name.')
            return False
        if not (last_name_match and len(last_name) > 1):
            print('Incorrect last name.')
            return False
        if not email_match:
            print('Incorrect email.')
            return False

        if email not in self.students_list:
            self.students_list[email] = {
                'student_id': self.student_id,
                'name': name,
                'last_name': last_name,
                'Python': {'Status': False, 'Points': 0},
                'DSA': {'Status': False, 'Points': 0},
                'Databases': {'Status': False, 'Points': 0},
                'Flask': {'Status': False, 'Points': 0}
            }
            self.student_id += 1
            print('The student has been added.')
            return True
        else:
            print('This email is already taken.')
            return False

    def check_input_points(self, input_string):
        input_list = input_string.split()
        if not input_list:
            print('Incorrect points format.')
            return

        try:
            student_id = int(input_list[0])
        except ValueError:
            print(f'No student is found for id={input_list[0]}.')
            return

        email = self.get_student_email_by_id(student_id)
        if not email:
            print(f'No student is found for id={student_id}.')
            return

        if len(input_list) != 5:
            print('Incorrect points format.')
            return

        try:
            python_points = int(input_list[1])
            dsa_points = int(input_list[2])
            databases_points = int(input_list[3])
            flask_points = int(input_list[4])
        except ValueError:
            print('Incorrect points format.')
            return

        if any(p < 0 for p in [python_points, dsa_points, databases_points, flask_points]):
            print('Incorrect points format.')
            return

        self.courses_list['Python'].append([student_id, python_points])
        self.students_list[email]['Python']['Points'] += python_points

        self.courses_list['DSA'].append([student_id, dsa_points])
        self.students_list[email]['DSA']['Points'] += dsa_points

        self.courses_list['Databases'].append([student_id, databases_points])
        self.students_list[email]['Databases']['Points'] += databases_points

        self.courses_list['Flask'].append([student_id, flask_points])
        self.students_list[email]['Flask']['Points'] += flask_points

        print('Points updated.')

    def handle_add_students(self):
        print('Enter student credentials or \'back\' to return:')
        added_students = 0
        while True:
            response = input()
            response_lower = response.lower()
            if response_lower == 'back':
                self.handle_back(verbose=True, added_students=added_students)
            else:
                try:
                    if self.check_input_string(response):
                        added_students += 1
                except (ValueError, IndexError):
                    print('Incorrect credentials.')

    def handle_back(self, verbose=False, added_students=0):
        if verbose:
            print(f'Total {added_students} students have been added.')
        self.run(print_greeting=False)

    def handle_list(self):
        print('Students:')
        if self.students_list:
            for val in self.students_list.values():
                print(val.get('student_id'))
        else:
            print('No students found.')

    def handle_statistics(self):
        print('Type the name of a course to see details or \'back\' to quit')

        # Gather statistics if all courses have some data
        if all(val for val in self.courses_list.values()):
            def enrolled_students_count(course_data):
                return len({c[0] for c in course_data if c[1] != 0})

            def endorsements_count(course_data):
                return len([c[0] for c in course_data if c[1] != 0])

            def average_points(course_data):
                non_zero_values = [c[1] for c in course_data if c[1] != 0]
                return sum(non_zero_values) / len(non_zero_values) if non_zero_values else 0

            courses = self.courses_list.keys()
            enrolled_counts = {c: enrolled_students_count(self.courses_list[c]) for c in courses}
            endorsement_counts = {c: endorsements_count(self.courses_list[c]) for c in courses}
            averages = {c: average_points(self.courses_list[c]) for c in courses}

            max_enrolled = max(enrolled_counts.values(), default=0)
            min_enrolled = min(enrolled_counts.values(), default=0)
            most_popular = [c for c, v in enrolled_counts.items() if v == max_enrolled]
            least_popular = [c for c, v in enrolled_counts.items() if v == min_enrolled]

            max_endorsements = max(endorsement_counts.values(), default=0)
            min_endorsements = min(endorsement_counts.values(), default=0)
            highest_student_activity = [c for c, v in endorsement_counts.items() if v == max_endorsements]
            lowest_student_activity = [c for c, v in endorsement_counts.items() if v == min_endorsements]

            max_average = max(averages.values(), default=0)
            min_average = min(averages.values(), default=0)
            easiest_course = [c for c, v in averages.items() if v == max_average]
            hardest_course = [c for c, v in averages.items() if v == min_average]

            print('Most popular: {}'.format(', '.join(most_popular)))
            if most_popular == least_popular:
                print('Least popular: n/a')
            else:
                print('Least popular: {}'.format(', '.join(least_popular)))

            print('Highest activity: {}'.format(', '.join(highest_student_activity)))
            if highest_student_activity == lowest_student_activity:
                print('Lowest activity: n/a')
            else:
                print('Lowest activity: {}'.format(', '.join(lowest_student_activity)))

            print('Easiest course: {}'.format(', '.join(easiest_course)))
            print('Hardest course: {}'.format(', '.join(hardest_course)))

        else:
            print('Most popular: n/a')
            print('Least popular: n/a')
            print('Highest activity: n/a')
            print('Lowest activity: n/a')
            print('Easiest course: n/a')
            print('Hardest course: n/a')

        while True:
            response = input()
            r_lower = response.lower()
            if r_lower == 'back':
                self.handle_back(verbose=False)
            elif r_lower in [course.lower() for course in self.courses_list]:
                for course in self.courses_list:
                    if r_lower == course.lower():
                        print(course)
                        print('{:5} {} {}'.format('id', 'points', 'completed'))
                        if all(val for val in self.courses_list.values()):
                            list_for_print = []
                            for value in self.students_list.values():
                                points = sum(v[1] for v in self.courses_list[course] if v[0] == value['student_id'])
                                points_percentage = points / self.points_list[course] * 100
                                list_for_print.append([value['student_id'], points, round(points_percentage, 1)])
                            list_for_print.sort(key=lambda x: (x[1], -x[0]), reverse=True)
                            for item in list_for_print:
                                print('{} {:<6} {}%'.format(item[0], item[1], item[2]))
                        break
            else:
                print('Unknown course.')

    def handle_find(self):
        print('Enter an id or \'back\' to return:')
        while True:
            response = input()
            r_lower = response.lower()
            if r_lower == 'back':
                self.handle_back()
            else:
                try:
                    s_id = int(response)
                    email = self.get_student_email_by_id(s_id)
                    if not email:
                        print(f'No student is found for id={response}.')
                    else:
                        python_points = sum([val[1] for val in self.courses_list.get('Python') if val[0] == s_id])
                        dsa_points = sum([val[1] for val in self.courses_list.get('DSA') if val[0] == s_id])
                        databases_points = sum([val[1] for val in self.courses_list.get('Databases') if val[0] == s_id])
                        flask_points = sum([val[1] for val in self.courses_list.get('Flask') if val[0] == s_id])
                        print(f'{s_id} points: Python={python_points}; DSA={dsa_points}; '
                              f'Databases={databases_points}; Flask={flask_points}')
                        # Deleting entry to mimic original logic (no functionality change)
                        del self.students_list[email]
                except (ValueError, IndexError):
                    print(f'No student is found for id={response}.')

    def handle_add_points(self):
        print('Enter an id and points or \'back\' to return:')
        while True:
            response = input()
            r_lower = response.lower()
            if r_lower == 'back':
                self.handle_back(verbose=False)
            else:
                self.check_input_points(response)

    def handle_notify(self):
        notified_students = 0
        for key, val in self.students_list.items():
            notification_status = False
            for course_name in ['Python', 'DSA', 'Databases', 'Flask']:
                if val[course_name]['Status'] is False and val[course_name]['Points'] >= self.points_list[course_name]:
                    notification_status = True
                    full_name = f"{val['name']} {val['last_name']}"
                    print(f'To: {key}')
                    print('Re: Your Learning Progress')
                    print(f'Hello, {full_name}! You have accomplished our {course_name} course!')
                    val[course_name]['Status'] = True

            if notification_status:
                notified_students += 1

        print(f'Total {notified_students} students have been notified.')

    @staticmethod
    def handle_exit():
        print('Bye!')
        quit()

    def input_handle(self):
        while True:
            command = input().strip().lower()
            if command == 'back':
                print('Enter \'exit\' to exit the program.')
            elif command in self.commands_list:
                try:
                    self.commands_list[command]()
                except TypeError:
                    print('Enter \'exit\' to exit the program.')
            elif not command or command.isspace():
                print('No input.')
            else:
                print('Error: unknown command!')

    def run(self, print_greeting=True):
        if print_greeting:
            self.print_greeting()
        self.input_handle()

def main():
    tracker = LearningProgressTracker()
    tracker.run()

if __name__ == '__main__':
    main()
