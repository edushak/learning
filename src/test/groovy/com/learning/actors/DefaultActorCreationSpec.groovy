package com.learning.actors

import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.dataflow.DataflowQueue
import groovyx.gpars.dataflow.DataflowVariable
import spock.lang.Ignore
import spock.lang.Specification

import static java.util.concurrent.TimeUnit.SECONDS

class DefaultActorCreationSpec extends Specification {

    void testCreationWithAct() {
        when:
        final def result = new DataflowVariable()
        final def thread = new DataflowVariable()
        final def actor = [act: {
            result << 'Received'
            thread << Thread.currentThread()
        }] as DefaultActor
        actor.start()
        then:
        assert actor.active
        assert result.val == 'Received'
        assert thread.val != Thread.currentThread()

        when:
        actor.join()
        then:
        assert !actor.active
    }

    void testCreationWithoutAct() {
        given:
        final def actor = new DefaultActor()
        final def error = new DataflowVariable()
        final def stopped = new DataflowVariable()
        actor.metaClass.onException {
            error << it
        }
        actor.metaClass.afterStop {
            stopped << true
        }
        when:
        actor.start()
        then:
        assert error.val instanceof UnsupportedOperationException

        when:
        stopped.val
        then:
        assert !actor.isActive()

        when:
        actor.send('Message')
        then:
        IllegalStateException ex = thrown()
        ex.message == "The actor cannot accept messages at this point."
    }

    void testCreationWithClosure() {
        when:
        final def result = new DataflowVariable()
        final def thread = new DataflowVariable()
        final def actor = new DefaultActor({
            result << 'Received'
            thread << Thread.currentThread()
        })
        actor.start()
        then:
        assert result.val == 'Received'
        assert thread.val != Thread.currentThread()

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testMessagingWithAct() {
        given:
        final def result = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
        })
        actor.start()
        when: "I send a message to Actor"
        actor 'Message'
        then:
        assert result.val == 'Message'

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testMessagingWithClosure() {
        given:
        final def result = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
        })
        actor.start()
        when:
        actor 'Message'
        then:
        assert result.val == 'Message'

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testNullMessagingWithAct() {
        given:
        final def result = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
        })
        actor.start()
        when:
        actor null
        then:
        assert result.val == null

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testNullMessagingWithClosure() {
        given:
        final def result = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
        })
        actor.start()
        when:
        actor null
        then:
        assert result.val == null

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    @Ignore
    void testLoopingWithAct() {
        given:
        final def resultQueue = new DataflowQueue() // SyncDataflowQueue
        final def actor = new DefaultActor({
            react {
                resultQueue << it
            }
        })
//        final def actor
//        actor = [act: {
//            actor.loop {
//                react {
//                    result << it
//                }
//            }
//        }] as DefaultActor
        actor.start()
        when:
        actor.send 'Message1'
        actor.send 'Message2'
        actor.send 'Message3'
        then:
        assert resultQueue.getVal(5, SECONDS) == 'Message1'
        assert resultQueue.getVal(5, SECONDS) == 'Message2'
        assert resultQueue.getVal(5, SECONDS) == 'Message3'

        when:
        actor.stop()
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testLoopingWithClosure() {
        given:
        final def result = new DataflowQueue()
        final def actor = new DefaultActor({
            loop {
                react {
                    result << it
                }
            }
        })
        actor.start()
        when:
        actor 'Message1'
        actor 'Message2'
        actor 'Message3'
        then:
        assert result.val == 'Message1'
        assert result.val == 'Message2'
        assert result.val == 'Message3'

        when: "message sent with a delay on a separate thread"
        Thread.start {
            sleep(5000)
            actor 'Message4'
        }
        then: "should wait for 5 sec until message is received"
        assert result.val == 'Message4'

        when:
        actor.stop()
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testRepliesWithAct() {
        when:
        final def actor = new DefaultActor({
            react {
                reply 'response: ' + it
                react {
                    sender << 'response: ' + it
                }
            }
        })
//        final def actor
//        actor = [act: {
//            actor.react {
//                reply 'response: ' + it
//                react {
//                    sender << 'response: ' + it
//                }
//            }
//        }] as DefaultActor
        actor.start()
        then:
        assert 'response: Message1' == actor.sendAndWait('Message1')
        assert 'response: Message2' == actor.sendAndWait('Message2')

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testRepliesWithClosure() {
        when:
        final def actor = new DefaultActor({
            react {
                reply 'response 1: ' + it
                react {
                    sender << 'response 2: ' + it // + ' response'
                }
            }
        })
        actor.start()
        then:
        assert 'response 1: Message1' == actor.sendAndWait('Message1')
        assert 'response 2: Message2' == actor.sendAndWait('Message2')

        when:
        actor.join()
        then:
        assert !actor.isActive()
    }

    void testContinuationStyleWithAct() {
        when:
        final def result = new DataflowVariable()
        final def continuationResult = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
            continuationResult << 'Reached'
        })
//        final def actor
//        actor = [act: {
//            actor.react {
//                result << it
//            }
//            continuationResult << 'Reached'
//        }] as DefaultActor
        actor.start()
        actor 'Message'
        then:
        assert result.val == 'Message'

        when:
        actor.join()
        then:
        assert !actor.isActive()
        assert continuationResult.isBound()
    }

//    void testContinuationStyleWithClosure() {
//        final def result = new DataflowVariable()
//        final def continuationResult = new DataflowVariable()
//        final def actor = new DefaultActor({
//            react {
//                result << it
//            }
//            continuationResult << 'Reached'
//        })
//        actor.start()
//        actor 'Message'
//        assert result.val == 'Message'
//        actor.join()
//        assert !actor.isActive()
//        assert continuationResult.isBound()
//    }
//
//    void testOnMessage() {
//        Actor actor  = new MyDispatchActor();
//        actor.start();
//
//        actor.send(17);
//        actor.send('Hello');
//
//        // actor.join()
//    }
//
//    void testMyDefaultActor() {
//        MyDefaultActor actor = new MyDefaultActor()
//        actor.start()
//
//        // blocks while waiting
//        assert 'got message 17' == actor.sendAndWait(17)
//        assert 'got message Hello' == actor.sendAndWait('Hello')
//    }
//
//    void testForkJoin() {
//        File rootDir = new File("C:\\Users\\edush\\GItHub\\learning\\src")
//        //feel free to experiment with the number of fork/join threads in the pool
//        withPool(3) {pool ->
//            println """Number of files: ${
//                runForkJoin(rootDir) {file ->
//                    long count = 0
//                    file.eachFile {
//                        if (it.isDirectory()) {
//                            println "Forking a child task for $it"
//                            forkOffChild(it)       //fork a child task
//                        } else {
//                            count++
//                        }
//                    }
//                    return count + (childrenResults.sum(0))
//                    //use results of children tasks to calculate and store own result
//                }
//            }"""
//        }
//    }
//
//    class MyDispatchActor extends DynamicDispatchActor {
//        def onMessage(int i) {
//            println('got int ' + i)
//            sleep(2000)
//            reply('got int ' + i)
//        }
//
//        def onMessage(String s) {
//            println('got string: ' + s)
//            sleep(2000)
//            reply('got string ' + s)
//        }
//    }
//
//    class MyDefaultActor extends DefaultActor {
//
//        void act() {
//            println("Started MyDefaultActor")
//            // do something on startup
//            loop {
//                react { def msg ->
//                    println "got message: $msg"
//                    reply('got message ' + msg)
//                }
//            }
//        }
///*
//        def onMessage(int i){
//            println('got int ' + i)
//        }
//        def onMessage(String s){
//            println('got string: ' + s)
//        }
//*/
//    }
}