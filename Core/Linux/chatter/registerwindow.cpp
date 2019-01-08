#include "registerwindow.h"
#include "ui_registerwindow.h"

RegisterWindow::RegisterWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::RegisterWindow),
    services(this)
{
    ui->setupUi(this);
    ui->labelError->hide();
}

RegisterWindow::~RegisterWindow()
{
    delete ui;
}

void RegisterWindow::on_backToLoginButton_clicked()
{
    this->close();
}

void RegisterWindow::on_registerButton_clicked()
{
    QString endpoint = QString("/api/RegisterUser");
    QJsonObject data;

    data["firstname"] = ui->firstNameText->text();
    data["secondname"] = ui->secondNameText->text();
    data["login"] = ui->loginText->text();
    data["password"] = ui->passwordText->text();
    data["email"] = ui->emailText->text();

    services.put(endpoint, data);
}
