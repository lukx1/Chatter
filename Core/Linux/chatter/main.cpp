#include "mainwindow.h"
#include "loginwindow.h"
#include "httpservice.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    MainWindow win;
    LoginWindow lwin(&win);
    if(win.IsLoginValid()) {
        win.show();
    } else {
        lwin.show();
    }

    return a.exec();
}
