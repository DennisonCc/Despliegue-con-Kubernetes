import psycopg2

dbs = [
    ('mc-clientes', 'localhost', 5434, 'db_parkin_users', 'parkin', 'qwerty123'),
    ('zone_core', 'localhost', 5435, 'db_parkin_zone', 'parkin', 'qwerty123'),
    ('ms-tickets', 'localhost', 5437, 'db_parkin_tickets', 'parkin', 'qwerty123')
]

for name, host, port, db, user, pwd in dbs:
    print(f"\n{name}:")
    try:
        conn = psycopg2.connect(host=host, port=port, database=db, user=user, password=pwd)
        cur = conn.cursor()
        cur.execute("SELECT tablename FROM pg_tables WHERE schemaname='public'")
        tables = cur.fetchall()
        for t in tables:
            print(f"  - {t[0]}")
        conn.close()
    except Exception as e:
        print(f"  Error: {e}")
