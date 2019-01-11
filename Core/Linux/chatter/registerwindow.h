#pragma once

#include <QMainWindow>
#include "httpservice.h"

namespace Ui {
class RegisterWindow;
}

class RegisterWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit RegisterWindow(QWidget *parent = nullptr);
    ~RegisterWindow();

private slots:
    void on_backToLoginButton_clicked();

    void on_registerButton_clicked();

private:
    Ui::RegisterWindow *ui;
    Chatter::HttpService services;
};
