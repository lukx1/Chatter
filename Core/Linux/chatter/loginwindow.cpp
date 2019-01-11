#include "loginwindow.h"
#include "ui_loginwindow.h"

#include "registerwindow.h"
#include "chatter.h"

#include <QDebug>

LoginWindow::LoginWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::LoginWindow),
    services(this)
{
    ui->setupUi(this);
}

LoginWindow::~LoginWindow()
{
    delete ui;
}

void LoginWindow::on_loginButton_clicked()
{
    QString endpoint = QString("/api/ValidateLogin");
    QJsonObject data;

    data["login"] = ui->usernameText->text();
    data["password"] = ui->passwordText->text();

    QNetworkReply* reply = services.post(endpoint, data);

    connect(reply, &QNetworkReply::finished, [this, reply]
    {
       if(reply->error() != QNetworkReply::NoError)
       {

       } else {
            //Chatter::Global::CONFIG.USERNAME = new QString(this->ui->usernameText->text());
            //Chatter::Global::CONFIG.PASSWORD = new QString(this->ui->passwordText->text());
       }
    });
}

void LoginWindow::on_registerButton_clicked()
{
    RegisterWindow *rwin = new RegisterWindow(this);
    rwin->show();
}
