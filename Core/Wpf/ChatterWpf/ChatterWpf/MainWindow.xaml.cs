using ChatterWpf.Models;
using ChatterWpf.Repos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Timers;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ChatterWpf
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private RepoRepo RepoRepo;
        private User LoggedInUser;
        private Room currentRoom = null;
        private Timer timer;

        public MainWindow()
        {
            RepoRepo = new RepoRepo(new Comms.Messenger("http://78.102.218.164:8080"));
            InitializeComponent();


        }

        public class Firend
        {
            public int ID { get; set; }
            public string Name { get; set; }
        }

        /*
         * RepoRepo.UserRepo.registerUser(user); 
         */

        private string userArrToStr(User[] users)
        {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < users.Length; i++)
            {
                if (i != 0)
                {
                    b.Append(", ");
                }
                b.Append(users[i].login);
            }
            return b.ToString();
        }

        private void loadRooms()
        {
            Room[] rooms = RepoRepo.RoomRepo.getRoomsWithUser(LoggedInUser.id);
            roomListView.Items.Clear();
            var gridView = new GridView();
            this.roomListView.View = gridView;
            //gridView.Columns.Add(new GridViewColumn
            //{
            //    Header = "ID",
            //    DisplayMemberBinding = new Binding("id")
            //});
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Name",
                DisplayMemberBinding = new Binding("name")
            });
            /*gridView.Columns.Add(new GridViewColumn
            {
                Header = "One on One",
                DisplayMemberBinding = new Binding("oneOnOne")
            });*/
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Users",
                DisplayMemberBinding = new Binding("users")
            });

            roomListView.MouseDoubleClick += RoomListView_MouseDoubleClick;

            var rs = new List<RoomShadow>();
            foreach (var room in rooms)
            {
                User[] usersInRoom = RepoRepo.RoomRepo.getUsersInRoom(room.id).Where(r => r.id != LoggedInUser.id).ToArray();
                if (room.oneOnOne)
                {

                    User other = usersInRoom.FirstOrDefault();
                    rs.Add(
                    new RoomShadow()
                    {
                        id = room.id,
                        name = other.login,
                        oneOnOne = room.oneOnOne,
                        users = userArrToStr(usersInRoom),
                        room = room,
                        otherUser = other
                    }
                    );
                }
                else
                {
                    rs.Add(
                        new RoomShadow()
                        {
                            id = room.id,
                            name = room.name,
                            oneOnOne = room.oneOnOne,
                            users = userArrToStr(usersInRoom),
                            room = room
                        }
                        );
                }
            }

            // Populate list
            //this.roomListView.Items.Add(new Firend { ID = 1, Name = "David" });
            foreach (var room in rs)
            {
                this.roomListView.Items.Add(room);
            }
        }

        public class RoomShadow
        {
            public int id { get; set; }
            public string name { get; set; }
            public bool oneOnOne { get; set; }
            public string users { get; set; }
            public Room room { get; set; }
            public User otherUser { get; set; }

            public RoomShadow()
            {

            }

        }

        private void RoomListView_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            var room = ((RoomShadow)roomListView.SelectedItem).room;
            currentRoom = room;
            rightPanelLabelName.Content = room.name;
            rightPanelLabelEmail.Content = room.oneOnOne;
            GetMessages(room.id);
        }

        private void loggedIn()
        {
            loadRooms();
            timer = new Timer();
            timer.Interval = 1000;
            timer.Elapsed += Timer_Elapsed;
            timer.Start();
        }

        private void Timer_Elapsed(object sender, ElapsedEventArgs e)
        {
            if (currentRoom != null)
            {
                Application.Current.Dispatcher.Invoke(new Action(() => { GetMessages(currentRoom.id); }));
            }
        }

        private void GetMessages(int id)
        {
            Message[] messages = RepoRepo.MessageRepo.getMessagesInRoom(id);
            User[] usersInRoom = RepoRepo.RoomRepo.getUsersInRoom(id);

            messageListView.Items.Clear();

            var gridView = new GridView();
            this.messageListView.View = gridView;
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "ID",
                Width = 0,
                DisplayMemberBinding = new Binding("id")
            });

            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Name",
                DisplayMemberBinding = new Binding("name")
            });
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "dateSent",
                DisplayMemberBinding = new Binding("sent")
            });
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Content",
                DisplayMemberBinding = new Binding("content")
            });


            // Populate list
            //this.roomListView.Items.Add(new Firend { ID = 1, Name = "David" });
            foreach (var message in messages)
            {
                if (message.content.Contains("!<"))
                {
                    continue;
                }
                var shadow = new ShadowMessage();
                shadow.content = message.content;
                shadow.id = message.idsender;
                shadow.sent = message.dateSent;
                shadow.name = usersInRoom.First(r => r.id == shadow.id).login;
                this.messageListView.Items.Add(shadow);
            }
        }

        private void ShowAllUsersInFriendsView()
        {
            Relationship[] relationships = RepoRepo.RelationshipRepo.getRelForUser(LoggedInUser.id);
            User[] usersInRoom = RepoRepo.UserRepo.getUsers();

            this.AddFriendsListView.Items.Clear();

            var gridView = new GridView();
            this.AddFriendsListView.View = gridView;
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Nickname",

                DisplayMemberBinding = new Binding("login")
            });
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "First Name",

                DisplayMemberBinding = new Binding("firstName")
            });
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Last Name",

                DisplayMemberBinding = new Binding("secondName")
            });
            gridView.Columns.Add(new GridViewColumn
            {
                Header = "Relationhip",

                DisplayMemberBinding = new Binding("Relationship")
            });

            AddFriendsListView.MouseDoubleClick += AddFriendsListView_MouseDoubleClick; ;

            var shList = new List<ShadowUserRel>();
            foreach (var user in usersInRoom)
            {
                shList.Add(
                    new ShadowUserRel()
                    {
                        firstName = user.firstName,
                        login = user.login,
                        secondName = user.secondName,
                        other = user,
                        Relationship = relationships.FirstOrDefault(r => r.idtargetUser == user.id) != null ? relationships.FirstOrDefault(r => r.idtargetUser == user.id).relationType.ToString() : "NONE"
                    }

                    );
            }

            foreach (var sh in shList)
            {
                AddFriendsListView.Items.Add(sh);
            }
        }

        private void AddFriendsListView_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            var other = ((ShadowUserRel)AddFriendsListView.SelectedItem).other;
            Relationship[] allMyRels = RepoRepo.RelationshipRepo.getRelForUser(LoggedInUser.id);
            Relationship withOther = allMyRels.FirstOrDefault(r => r.idtargetUser == other.id);
            if (withOther == null)
            {
                withOther = new Relationship()
                {
                    dateCreated = DateTime.Now,
                    idsourceUser = LoggedInUser.id,
                    idtargetUser = other.id,
                    relationType = RelStatus.FRIEND
                };
                RepoRepo.RelationshipRepo.addRel(withOther);
            }
            else // Not null
            {
                withOther.relationType = RelStatus.FRIEND;
                RepoRepo.RelationshipRepo.setRel(withOther);
            }
            loadRooms();
            stackPanelLeftAddFriends.Visibility = Visibility.Collapsed;
            leftStackPanelGroups.Visibility = Visibility.Visible;
        }

        public class ShadowUserRel
        {
            public string login { get; set; }
            public string firstName { get; set; }
            public string secondName { get; set; }
            public string Relationship { get; set; }
            public User other { get; set; }
        }

        public class ShadowMessage
        {
            public int id { get; set; }
            public string name { get; set; }
            public string content { get; set; }
            public DateTime? sent { get; set; }

        }

        private void GetFriends(int id)
        {


        }

        private void LoginButtonLogin_Click(object sender, RoutedEventArgs e)
        {

            string login = loginTextBoxInputName.Text;
            string pass = loginTextBoxInputPassword.Password;
            try
            {
                if (RepoRepo.tryLogin(login, pass))
                {
                    LoggedInUser = RepoRepo.UserRepo.getUserWithLogin(login);
                    //TODO(Petr):Handle LOGIC
                    if (LoggedInUser != null)
                    {
                        this.loginBorder.Visibility = Visibility.Collapsed;
                        this.contentHolder.Visibility = Visibility.Visible;
                        topBarLabel.Content = LoggedInUser.firstName + " " + LoggedInUser.secondName;
                        loggedIn();
                    }
                }
                else
                {
                    //BAD LOGIN
                    throw new Exception("Bad login");
                }
            }
            catch (Exception ex)
            {
                throw ex;
                //THROW EXCEPTION SPATNY LOGIN
            }
        }

        private void RegisterButtonRegister_Click(object sender, RoutedEventArgs e)
        {
            User registeruser = new User();
            registeruser.login = registerInputNickname.Text;
            registeruser.firstName = registerInputName.Text;
            registeruser.secondName = registerInputSurname.Text;
            registeruser.email = registerInputEmail.Text;
            registeruser.password = registerInputPassword.Password;
            RepoRepo.UserRepo.registerUser(registeruser);
            registerInputNickname.Text = "";
            registerInputName.Text = "";
            registerInputSurname.Text = "";
            registerInputEmail.Text = "";
            registerInputPassword.Password = "";


        }

        private void LoginButtonRegister_Click(object sender, RoutedEventArgs e)
        {
            loginBorder.Visibility = Visibility.Collapsed;
            registerBorder.Visibility = Visibility.Visible;
        }

        private void RegisterButtonBack_Click(object sender, RoutedEventArgs e)
        {
            registerBorder.Visibility = Visibility.Collapsed;
            loginBorder.Visibility = Visibility.Visible;

        }

        private void ButtonGroups_Click(object sender, RoutedEventArgs e)
        {

        }

        private void ButtonFriends_Click(object sender, RoutedEventArgs e)
        {

        }

        private void ButtonShowAddFriends_Click(object sender, RoutedEventArgs e)
        {
            leftStackPanelGroups.Visibility = Visibility.Collapsed;
            stackPanelLeftAddFriends.Visibility = Visibility.Visible;
            ShowAllUsersInFriendsView();

        }

        private void RightPanelButtonAddRemoveFriend_Click(object sender, RoutedEventArgs e)
        {
            if (currentRoom != null && LoggedInUser != null)
            {
                RepoRepo.RoomRepo.removeUserFromRoom(LoggedInUser.id, currentRoom.id);
                loadRooms();
            }
        }

        private void RightPanelButtonBlock_Click(object sender, RoutedEventArgs e)
        {

        }

        private void ButtonSendMessage_Click(object sender, RoutedEventArgs e)
        {
            if (currentRoom == null)
                return;

            Message message = new Message();
            
            message.dateSent = DateTime.Now;
            message.content = textBoxMessage.Text;
            
            message.idsender = LoggedInUser.id;
            message.idroomReceiver = currentRoom.id;
            RepoRepo.MessageRepo.addMessage(message);
            textBoxMessage.Text = "";

        }

        private void ButtonSearchFriends_Click(object sender, RoutedEventArgs e)
        {
            

       
        }

        private void ButtonAddFriendsAdd_Click(object sender, RoutedEventArgs e)
        {
           
        }

        private void ButtonAddFriendsBack_Click(object sender, RoutedEventArgs e)
        {
            stackPanelLeftAddFriends.Visibility = Visibility.Collapsed;
            leftStackPanelGroups.Visibility = Visibility.Visible;

        }

        private void ButtonSearchFriends_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {

           
        }

        private void TopBarButtonLogout_Click(object sender, RoutedEventArgs e)
        {
            contentHolder.Visibility = Visibility.Collapsed;
            loginBorder.Visibility = Visibility.Visible;
            LoggedInUser = null;
            loginTextBoxInputName.Text = "";
            loginTextBoxInputPassword.Password = "";
        }

      
    }
}

