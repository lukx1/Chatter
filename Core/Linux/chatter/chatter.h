#pragma once

#include "defines.h"

#include "httpservice.h"

CHATTER_NAMESPACE_BEGIN

class Config
{
public:
    Config()
    {
    }

    QString *USERNAME = nullptr;
    QString *PASSWORD = nullptr;

};

class Global
{
    Global() = delete;

public:
    static Config CONFIG;
};

CHATTER_NAMESPACE_END
