#pragma once

#include "httpservice.h"

#include <QMainWindow>

namespace Ui {
class LoginWindow;
}

class LoginWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit LoginWindow(QWidget *parent = nullptr);
    ~LoginWindow();

private slots:
    void on_loginButton_clicked();

    void on_registerButton_clicked();

private:
    Ui::LoginWindow *ui;
    Chatter::HttpService services;

};
