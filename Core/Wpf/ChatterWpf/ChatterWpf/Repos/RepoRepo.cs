using ChatterWpf.Comms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ChatterWpf.Repos
{
    public class RepoRepo
    {
        public CFileRepo CFileRepo { get; private set; }
        public MessageRepo MessageRepo { get; private set; }
        public RelationshipRepo RelationshipRepo { get; private set; }
        public RoomRepo RoomRepo { get; private set; }
        public UserRepo UserRepo { get; private set; }

        private List<AbstractRepo> repos = new List<AbstractRepo>(5);

        public RepoRepo(Messenger messenger)
        {
            CFileRepo = new CFileRepo(messenger);
            MessageRepo = new MessageRepo(messenger);
            RelationshipRepo = new RelationshipRepo(messenger);
            RoomRepo = new RoomRepo(messenger);
            UserRepo = new UserRepo(messenger);

            repos.Add(CFileRepo);
            repos.Add(MessageRepo);
            repos.Add(RelationshipRepo);
            repos.Add(RoomRepo);
            repos.Add(UserRepo);
        }

        public bool tryLogin(String username, String password)
        {
            string oldLogin = UserRepo.loginHeader.Login;
            string oldPassword = UserRepo.loginHeader.Login;

            Init(username, password);

            if (!UserRepo.validateLogin())
            {
                Init(oldLogin, oldPassword);
                return false;
            }
            else
            {
                return true;
            }
        }

        public void Init(string login, string password)
        {
            foreach (var repo in repos)
            {
                repo.loginHeader.Login = login;
                repo.loginHeader.Password = password;
            }
        }

    }
}
