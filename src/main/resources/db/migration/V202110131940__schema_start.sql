SET TIMEZONE='Brazil/East';
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION pgcrypto;

CREATE TABLE public.product (
    id      UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name    varchar(50) NOT NULL,
    service BOOLEAN NOT NULL DEFAULT FALSE,
    valor   NUMERIC NOT NULL
);

CREATE TABLE public.solicitation (
    id     UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name   varchar(50) NOT NULL,
    status INT NOT NULL,
    value   NUMERIC DEFAULT 0,
    value_with_discount NUMERIC DEFAULT 0
);

CREATE TABLE public.item_solicitation (
    id         UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    id_product UUID,
    id_solicitation   UUID,
    FOREIGN KEY (id_product) REFERENCES public.product (id),
    FOREIGN KEY (id_solicitation) REFERENCES public.solicitation (id)
);