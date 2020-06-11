using NetTopologySuite.Geometries;
using OSMLSGlobalLibrary;
using OSMLSGlobalLibrary.Map;
using OSMLSGlobalLibrary.Modules;
using System;
using System.Collections.Generic;

namespace TestModule
{
    public class TestModule : OSMLSModule
    {
        Shop shop;
        Factory factory;
        Storage storage;


        List<Customer> customers;
        Coordinate customerStartCoord = new Coordinate(4964618, 6197949);
        //Coordinate customerStartCoord = new Coordinate(4960405, 6195000);
        
        Truck truck;

        
        Coordinate factoryStart = new Coordinate(4965377, 6198207);
        //Coordinate factoryStart = new Coordinate(4961377, 6195258);
        Random random;
        int time = 0;

        protected override void Initialize()
        {
            random = new Random(10);

            // создание магазина
            
            shop = new Shop(4964768, 6198228, random);
            //shop = new Shop(4960555, 6195279, random);
            MapObjects.Add(shop);

            //создание склада
            
            storage = new Storage(4964664, 6198067);
            //storage = new Storage(4960451, 6195118);
            MapObjects.Add(storage);

            //создание завода
            
            factory = new Factory(4965509, 6198173);
            //factory = new Factory(4961296, 6195224);
            MapObjects.Add(factory);

            //создание грузовика
            truck = new Truck(factoryStart, 2);
            MapObjects.Add(truck);

            customers = new List<Customer>();
            //создание первого покупателя
            var customer = new Customer(customerStartCoord.Copy(), 1, 1000, customers.Count);
            MapObjects.Add(customer);
            customers.Add(customer);
        }

        /// <summary>
        /// Вызывается постоянно, здесь можно реализовывать логику перемещений и всего остального, требующего времени.
        /// </summary>
        /// <param name="elapsedMilliseconds">TimeNow.ElapsedMilliseconds</param>
        public override void Update(long elapsedMilliseconds)
        {
            truck.delivering(MapObjects, truck); // делаем поставки грузовиком

            time++;

            if (time % 320 == 0) //периодически создаем новых покупателей
            {
                time = 0;

                int money = random.Next(0, 1000);
                var customer = new Customer(customerStartCoord.Copy(), 1, money, customers.Count);
                customers.Add(customer);
                MapObjects.Add(customer);
            }

            foreach (Customer customer in customers)
            {
                customer.doingSmthg(shop); //в зависимости от разных условий, покупатель делает разные вещи

                if (customer.boat != null && !customer.BoatIsAdded)//Если лодка есть, но она ещё не была добавлена на карту
                {
                    MapObjects.Add(customer.boat);
                    customer.BoatIsAdded = true;
                }

                if (customer.BoatIsBroken)//Если лодка сломалась
                {
                    MapObjects.Remove(customer.boat);
                    MapObjects.Remove(customer);
                }
            }
        }
    }

    #region Truck
    [CustomStyle(
        @"new ol.style.Style({
            image: new ol.style.Circle({
                opacity: 1.0,
                scale: 1.0,
                radius: 4,
                fill: new ol.style.Fill({
                    color: 'rgba(0, 0, 0, 0.4)'
                }),
                stroke: new ol.style.Stroke({
                    color: 'rgba(0, 0, 0, 0.4)',
                    width: 1
                }),
            })
        });
        ")]


    class Truck : Point //класс грузовика, поставляющего лодки на склад
    {
        private int time = 0;
        private bool delivered = false;
        private Coordinate storageStart = new Coordinate(4964752, 6197981);
        //private Coordinate wareStart = new Coordinate(4960539, 6195032);
        private Coordinate factoryStart = new Coordinate(4965590, 6198207);
        //private Coordinate plantStart = new Coordinate(4961377, 6195258);
        public double Speed { get; }

        public Truck(Coordinate coordinate, double speed) : base(coordinate)
        {
            //Необходимо переопределять в переменные класса(Speed) для дальнейшего хранения в объекте подобных параметров
            Speed = speed;
        }

        public void delivering(IInheritanceTreeCollection<Geometry> MapObjects, Truck truck)
        {
            Point closestObj;
            // в зависимости от bool delivered определяем, куда поедет грузовик
            //delivered меняется на противоположный при достижении какого-илбо пункта назначения(склад или завод)
            if (delivered) closestObj = new Point(factoryStart);
            else closestObj = new Point(storageStart);

            Coordinate relativeCoord = new Coordinate(closestObj.X - X, closestObj.Y - Y);

            double curSpeedX = Math.Abs(relativeCoord.X) > Speed ? Speed : Speed / 2;
            double curSpeedY = Math.Abs(relativeCoord.Y) > Speed ? Speed : Speed / 2;

            //двигаем грузовик
            X += curSpeedX * Math.Sign(relativeCoord.X);
            Y += (curSpeedY * Math.Sign(relativeCoord.Y)) / 4;


            if (Coordinate.Distance(closestObj.Coordinate) < 2) // если добрался до точки назначения
            {
                time++;
                if (time > 200 && Coordinate.Distance(factoryStart) < 3) // если немного постоял около завода
                {
                    if (time == 201)
                    {
                        Console.WriteLine("Водитель грузовика идет отдохнуть \n");
                        MapObjects.Remove(truck);
                    }


                    if (time == 400)
                    {
                        Console.WriteLine("Грузовик отправляется на склад с грузом \n");
                        MapObjects.Add(truck);
                        delivered = false;
                        time = 0;
                    }

                }
                else if (time > 200 && Coordinate.Distance(storageStart) < 3) // если немного постоял около склада
                {
                    Console.WriteLine("Груз с лодками доставлен на склад \n");
                    delivered = true;
                    time = 0;
                }
            }
        }
    }
    #endregion

    #region Boat

    [CustomStyle(
    @"new ol.style.Style({
            image: new ol.style.Circle({
                opacity: 1.0,
                scale: 1.5,
                radius: 4,
                fill: new ol.style.Fill({
                    color: 'rgba(255, 255, 0, 0.4)'
                }),
                stroke: new ol.style.Stroke({
                    color: 'rgba(0, 0, 0, 0.4)',
                    width: 1
                }),
            })
        });
        ")]

    class Boat : Point //родительский класс для видов лодок
    {
        public double Speed { get; }//Скорость передвижения
        public double FailureChance { get; }//Шанс поломки
        public double Price { get; set; }//Цена
        public Random Random { get; }
        public Boat(Coordinate coordinate, double speed, double price, double failureChance, Random random) : base(coordinate)
        {
            Speed = speed;
            Price = price;
            Random = random;
            FailureChance = failureChance;
        }

        public bool CheckFailure()//Проверка поломки
        {
            if (Random.NextDouble() <= FailureChance)//Если полученное значение меньше шанса поломки
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    #endregion

    #region Raft
    [CustomStyle(
        @"new ol.style.Style({
            image: new ol.style.Circle({
                opacity: 1.0,
                scale: 1.0,
                radius: 5,
                fill: new ol.style.Fill({
                    color: 'rgba(139, 69, 19, 1)'
                }),
                stroke: new ol.style.Stroke({
                    color: 'rgba(0, 0, 0, 1)',
                    width: 1
                }),
            })
        });
        ")]
    class Raft : Boat//Скорость 1, цена 100, шанс поломки 0,001
    {
        public Raft(Coordinate coordinate, Random random) : base(coordinate, 1, 100, 0.001, random)
        {

        }
    }
    #endregion 

    #region RowingBoat
    [CustomStyle(
        @"new ol.style.Style({
            image: new ol.style.Circle({
                opacity: 1.0,
                scale: 1.0,
                radius: 5,
                fill: new ol.style.Fill({
                    color: 'rgba(0, 255, 255, 1)'
                }),
                stroke: new ol.style.Stroke({
                    color: 'rgba(0, 0, 0, 1)',
                    width: 1
                }),
            })
        });
        ")]
    class RowingBoat : Boat//Скорость 2, цена 500, шанс поломки 0,0001
    {
        public RowingBoat(Coordinate coordinate, Random random) : base(coordinate, 2, 500, 0.0001, random)
        {

        }
    }
    #endregion

    #region Catamaran
    [CustomStyle(
        @"new ol.style.Style({
            image: new ol.style.Circle({
                opacity: 1.0,
                scale: 1.0,
                radius: 5,
                fill: new ol.style.Fill({
                    color: 'rgba(0, 0, 255, 1)'
                }),
                stroke: new ol.style.Stroke({
                    color: 'rgba(0, 0, 0, 1)',
                    width: 1
                }),
            })
        });
        ")]
    class Catamaran : Boat //Скорость 2, цена 1000, шанс поломки 0,00001
    {
        public Catamaran(Coordinate coordinate, Random random) : base(coordinate, 2, 1000, 0.00001, random)
        {

        }
    }
    #endregion

    #region Customer

    [CustomStyle(
    @"new ol.style.Style({
            image: new ol.style.Circle({
                opacity: 1.0,
                scale: 1.5,
                radius: 4,
                fill: new ol.style.Fill({
                    color: 'rgba(255, 0, 0, 0.4)'
                }),
                stroke: new ol.style.Stroke({
                    color: 'rgba(0, 0, 0, 0.4)',
                    width: 1
                }),
            })
        });
        ")]

    class Customer : Point
    {
        public Boat boat { get; set; }//Купленный плот
        private Coordinate shopEntr = new Coordinate(4964618, 6198080);
        //private Coordinate shopEntr = new Coordinate(4960405, 6195141);
        private int Number { get; }
        private int Money { get; set; }
        public bool BoatIsAdded { get; set; }
        public bool BoatIsBroken { get; set; }
        public double Speed { get; }
        private int time = 0;
        public Customer(Coordinate coordinate, double speed, int money, int number) : base(coordinate)
        {
            Money = money;
            Number = number;
            BoatIsAdded = false;
            BoatIsBroken = false;
            Speed = speed;
            Console.WriteLine($"[Покупатель {number}] появился\n");
        }

        public void doingSmthg(Shop shop)
        {
            if (boat == null) //лодка ещё не куплена
            {
                if (!Coordinate.Equals(shopEntr)) //Ещё не подошёл ко входу
                {
                    moveToShop();
                }
                else if (BoatIsBroken)
                {
                    //лодка сломлена
                }
                else
                {
                    Console.WriteLine($"[Покупатель {Number}] дошел до магазина\n");
                    BuyRaft(shop);
                }
            }
            else
            {
                if (boat.CheckFailure())//Если лодка не прошла проверку
                {
                    BoatIsBroken = true;//Сломать лодку
                    Console.WriteLine($"[У Покупателя {Number}] сломалась лодка\n");
                }
                else
                {
                    //двигаем покупателя и лодку
                    boat.Coordinate.X -= boat.Speed;
                    boat.Coordinate.Y += boat.Speed;
                    Coordinate.X = boat.Coordinate.X;
                    Coordinate.Y = boat.Coordinate.Y;
                }
            }
        }

        public void moveToShop()
        {
            time++;
            if (time % 100 == 0)
            {
                Console.WriteLine($"[Покупатель {Number}] идёт ко входу в магазин\n");
            }

            Y += Speed;
        }
        public void BuyRaft(Shop shop)//Купить мотоцикл в магазине
        {
            boat = shop.sellBoat(Money);
            Console.WriteLine($"[Покупатель {Number}] плывет на новой лодке\n");
            Money -= (int)boat.Price; //Отнять от денег цену лодки
        }
    }

    #endregion

    #region Shop

    [CustomStyle(
        @"new ol.style.Style({
            fill: new ol.style.Fill(
            {
                    color: 'rgba(0, 255, 0, 0.9)'
            }),
            stroke: new ol.style.Stroke(
            {
                    color: 'rgba(0, 0, 0, 1)',
                    width: 1
            }),
        });
        ")]

    class Shop : Polygon
    {
        private Random Random { get; }
        public Shop(int X, int Y, Random random)
            : base(new LinearRing(
                new Coordinate[] {
                    new Coordinate(X, Y),
                    new Coordinate(X - 200, Y - 45),
                    new Coordinate(X - 168, Y - 177),
                    new Coordinate(X + 37, Y - 124),
                    new Coordinate(X, Y)
            }))
        {
            Random = random;
        }
        public Boat sellBoat(int money)//Продаём лодку
        {
            
            var boatCoord = new Coordinate(4964618, 6198090);
            //var boatCoord = new Coordinate(4960405, 6195141);

            //Смотрим на какую лодку хватит денег у покупателя
            if (money >= 1000)
            {
                Console.WriteLine("Был куплен катамаран за 1000\n");
                return new Catamaran(boatCoord, this.Random);
            }

            else if (money >= 100 && money < 1000)
            {
                Console.WriteLine("Была куплена весельная лодка в пределах от 100 до 1000\n");
                return new RowingBoat(boatCoord, this.Random);
            }
            else
            {
                Console.WriteLine("Был куплен плот за  100\n");
                return new Raft(boatCoord, this.Random);
            }
        }
    }
    #endregion

    #region Factory

    [CustomStyle(
        @"new ol.style.Style({
            fill: new ol.style.Fill(
            {
                    color: 'rgba(128, 0, 128, 0.9)'
            }),
            stroke: new ol.style.Stroke(
            {
                    color: 'rgba(0, 0, 0, 1)',
                    width: 1
            }),
        });
        ")]

    class Factory : Polygon
    {
        public Factory(int X, int Y)
            : base(new LinearRing(
                new Coordinate[] {
                    new Coordinate(X, Y),
                    new Coordinate(X + 100, Y + 50),
                    new Coordinate(X + 200, Y - 100),
                    new Coordinate(X + 10, Y - 100),
                    new Coordinate(X, Y)
            })) 
        {

        }

    }

    #endregion

    #region Storage

    [CustomStyle(
        @"new ol.style.Style({
            fill: new ol.style.Fill(
            {
                    color: 'rgba(119, 136, 153, 0.9)'
            }),
            stroke: new ol.style.Stroke(
            {
                    color: 'rgba(0, 0, 0, 1)',
                    width: 1
            }),
        });
        ")]

    class Storage : Polygon
    {
        public Storage(int X, int Y)
            : base(new LinearRing(
                new Coordinate[] {
                    new Coordinate(X, Y),
                    new Coordinate(X + 140, Y + 37),
                    new Coordinate(X + 166, Y - 60),
                    new Coordinate(X + 28, Y - 96),
                    new Coordinate(X, Y)
            }))
        {

        }
    }

    #endregion

}


