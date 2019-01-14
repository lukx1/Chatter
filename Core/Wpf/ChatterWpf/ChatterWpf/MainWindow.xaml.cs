using ChatterWpf.Models;
using ChatterWpf.Repos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
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

        public MainWindow()
        {
            RepoRepo = new RepoRepo(new Comms.Messenger("http://78.102.218.164:8080"));
            InitializeComponent();
        }

        /*
         * RepoRepo.UserRepo.registerUser(user); 
         */

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
                        loginTestInput.Text = LoggedInUser.login;
                    }
                }
                else
                {
                    //BAD LOGIN
                    throw new Exception("Bad login");
                }
            }
            catch( Exception ex)
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
    }
}

