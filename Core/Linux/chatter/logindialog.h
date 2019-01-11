#ifndef LOGINDIALOG_H
#define LOGINDIALOG_H

#include <QDialog>

namespace Ui {
class LoginDialog;
}

class LoginDialog : public QDialog
{
    Q_OBJECT

public:
    explicit LoginDialog(QWidget *parent = nullptr);
    ~LoginDialog();

private slots:
    void on_loginButton_clicked();

    void on_registerButton_clicked();

    void on_loginText_textEdited(const QString &arg1);

    void on_passwordText_textEdited(const QString &arg1);

private:
    Ui::LoginDialog *ui;
};

#endif // LOGINDIALOG_H
