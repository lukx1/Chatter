#include "logindialog.h"
#include "ui_logindialog.h"

LoginDialog::LoginDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::LoginDialog)
{
    ui->setupUi(this);
}

LoginDialog::~LoginDialog()
{
    delete ui;
}

void LoginDialog::on_loginButton_clicked()
{

}

void LoginDialog::on_registerButton_clicked()
{

}

void LoginDialog::on_loginText_textEdited(const QString &arg1)
{
    ui->loginButton->setEnabled(!arg1.isEmpty() && !ui->loginText->text().isEmpty());
}

void LoginDialog::on_passwordText_textEdited(const QString &arg1)
{
    ui->loginButton->setEnabled(!arg1.isEmpty() && !ui->passwordText->text().isEmpty());
}
