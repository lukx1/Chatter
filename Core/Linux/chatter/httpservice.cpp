#include "httpservice.h"

CHATTER_NAMESPACE_BEGIN

HttpService::HttpService(QObject *parent)
    : QObject(parent)
{
    connect(&network_access_manager, &QNetworkAccessManager::finished, this, &HttpService::onReply);
}

QNetworkReply *HttpService::get(const QString& endpoint, const QJsonObject& payload)
{
    QUrl url(BASE_URL);
    url.setPath(endpoint);

    return sendRequest(url, "GET", payload);
}

QNetworkReply *HttpService::post(const QString& endpoint, const QJsonObject& payload)
{
    QUrl url(BASE_URL);
    url.setPath(endpoint);

    return sendRequest(url, "POST", payload);
}

QNetworkReply *HttpService::del(const QString& endpoint, const QJsonObject& payload)
{
    QUrl url(BASE_URL);
    url.setPath(endpoint);

    return sendRequest(url, "DELETE", payload);
}

QNetworkReply *HttpService::patch(const QString& endpoint, const QJsonObject& payload)
{
    QUrl url(BASE_URL);
    url.setPath(endpoint);

    return sendRequest(url, "PATCH", payload);
}

QNetworkReply *HttpService::put(const QString& endpoint, const QJsonObject& payload)
{
    QUrl url(BASE_URL);
    url.setPath(endpoint);

    return sendRequest(url, "PUT", payload);
}

void HttpService::onReply(QNetworkReply *reply)
{
#ifdef QT_DEBUG
    if(reply->error() != QNetworkReply::NoError)
    {
        qDebug() << qPrintable(reply->errorString());
    }
#endif

    reply->deleteLater();
}

QNetworkReply *HttpService::sendRequest(QUrl& url, const QByteArray& verb, const QJsonObject& payload)
{
    QNetworkRequest request(url.url());

    if (payload.empty())
    {
        return network_access_manager.sendCustomRequest(request, verb);
    }
    else
    {
        request.setRawHeader("Content-Type", "application/json");
        QBuffer* buf = new QBuffer();
        buf->open(QBuffer::WriteOnly);
        buf->write(QJsonDocument(payload).toJson(QJsonDocument::Compact));
        buf->close();
        buf->open(QBuffer::ReadOnly);
        QNetworkReply* reply = network_access_manager.sendCustomRequest(request, verb, buf);
        connect(reply, &QNetworkReply::finished, [buf] { delete buf; });
        return reply;
    }
}

CHATTER_NAMESPACE_END
