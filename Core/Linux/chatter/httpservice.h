#pragma once

#include "defines.h"

CHATTER_NAMESPACE_BEGIN

class HttpService : public QObject
{
public:
    HttpService(QObject *parent = nullptr);

    QNetworkReply *get(const QString& endpoint, const QJsonObject& payload = QJsonObject());
    QNetworkReply *post(const QString& endpoint, const QJsonObject& payload = QJsonObject());
    QNetworkReply *del(const QString& endpoint, const QJsonObject& payload = QJsonObject());
    QNetworkReply *patch(const QString& endpoint, const QJsonObject& payload = QJsonObject());
    QNetworkReply *put(const QString& endpoint, const QJsonObject& payload = QJsonObject());

private slots:
    void onReply(QNetworkReply *reply);

private:
    QNetworkReply *sendRequest(QUrl& url, const QByteArray& verb, const QJsonObject& payload = QJsonObject());

    QNetworkAccessManager network_access_manager;
};

CHATTER_NAMESPACE_END
