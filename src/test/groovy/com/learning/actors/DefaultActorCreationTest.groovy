package com.learning.actors

import static groovyx.gpars.GParsPool.runForkJoin
import static groovyx.gpars.GParsPool.withPool

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.actor.DynamicDispatchActor
import groovyx.gpars.dataflow.DataflowQueue
import groovyx.gpars.dataflow.DataflowVariable

class DefaultActorCreationTest extends GroovyTestCase {

    void testCreationWithAct() {
        final def result = new DataflowVariable()
        final def thread = new DataflowVariable()
        final def actor = [act: {
            result << 'Received'
            thread << Thread.currentThread()
        }] as DefaultActor
        actor.start()
        assert actor.active
        assert result.val == 'Received'
        assert thread.val != Thread.currentThread()
        actor.join()

        assert !actor.active
    }

    void testCreationWithoutAct() {
        final def actor = new DefaultActor()

        final def error = new DataflowVariable()
        final def stopped = new DataflowVariable()
        actor.metaClass.onException {
            error << it
        }
        actor.metaClass.afterStop {
            stopped << true
        }
        actor.start()
        assert error.val instanceof UnsupportedOperationException
        stopped.val
        assert !actor.isActive()

        shouldFail(IllegalStateException) {
            actor 'Message'
        }
        assert !actor.isActive()
    }

    void testCreationWithClosure() {
        final def result = new DataflowVariable()
        final def thread = new DataflowVariable()
        final def actor = new DefaultActor({
            result << 'Received'
            thread << Thread.currentThread()
        })
        actor.start()
        assert result.val == 'Received'
        assert thread.val != Thread.currentThread()
        actor.join()
        assert !actor.isActive()
    }

    void testMessagingWithAct() {
        final def result = new DataflowVariable()
        final def actor
        actor = [act: {
            actor.react {
                result << it
            }
        }] as DefaultActor
        actor.start()
        actor 'Message'
        assert result.val == 'Message'
        actor.join()
        assert !actor.isActive()
    }

    void testMessagingWithClosure() {
        final def result = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
        })
        actor.start()
        actor 'Message'
        assert result.val == 'Message'
        actor.join()
        assert !actor.isActive()
    }

    void testNullMessagingWithAct() {
        final def result = new DataflowVariable()
        final def actor
        actor = [act: {
            actor.react {
                result << it
            }
        }] as DefaultActor
        actor.start()
        actor null
        assert result.val == null
        actor.join()
        assert !actor.isActive()
    }

    void testNullMessagingWithClosure() {
        final def result = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
        })
        actor.start()
        actor null
        assert result.val == null
        actor.join()
        assert !actor.isActive()
    }

    void testLoopingWithAct() {
        final def result = new DataflowQueue()
        final def actor
        actor = [act: {
            actor.loop {
                react {
                    result << it
                }
            }
        }] as DefaultActor
        actor.start()
        actor 'Message1'
        actor 'Message2'
        actor 'Message3'
        assert result.val == 'Message1'
        assert result.val == 'Message2'
        assert result.val == 'Message3'
        actor.stop()
        actor.join()
        assert !actor.isActive()
    }

    void testLoopingWithClosure() {
        final def result = new DataflowQueue()
        final def actor = new DefaultActor({
            loop {
                react {
                    result << it
                }
            }
        })
        actor.start()
        actor 'Message1'
        actor 'Message2'
        actor 'Message3'
        assert result.val == 'Message1'
        assert result.val == 'Message2'
        assert result.val == 'Message3'
        Thread.start {
            sleep(5000)
            actor 'Message4'
        }
        assert result.val == 'Message4'
        actor.stop()
        actor.join()
        assert !actor.isActive()
    }

    void testRepliesWithAct() {
        final def actor
        actor = [act: {
            actor.react {
                reply 'response: ' + it
                react {
                    sender << 'response: ' + it
                }
            }
        }] as DefaultActor
        actor.start()
        assert 'response: Message1' == actor.sendAndWait('Message1')
        assert 'response: Message2' == actor.sendAndWait('Message2')
        actor.join()
        assert !actor.isActive()
    }

    void testRepliesWithClosure() {
        final def actor = new DefaultActor({
            react {
                reply it
                react {
                    sender << it
                }
            }
        })
        actor.start()
        assert 'Message1' == actor.sendAndWait('Message1')
        assert 'Message2' == actor.sendAndWait('Message2')
        actor.join()
        assert !actor.isActive()
    }

    void testContinuationStyleWithAct() {
        final def result = new DataflowVariable()
        final def continuationResult = new DataflowVariable()
        final def actor
        actor = [act: {
            actor.react {
                result << it
            }
            continuationResult << 'Reached'
        }] as DefaultActor
        actor.start()
        actor 'Message'
        assert result.val == 'Message'
        actor.join()
        assert !actor.isActive()
        assert continuationResult.isBound()
    }

    void testContinuationStyleWithClosure() {
        final def result = new DataflowVariable()
        final def continuationResult = new DataflowVariable()
        final def actor = new DefaultActor({
            react {
                result << it
            }
            continuationResult << 'Reached'
        })
        actor.start()
        actor 'Message'
        assert result.val == 'Message'
        actor.join()
        assert !actor.isActive()
        assert continuationResult.isBound()
    }

    void testOnMessage() {
        Actor actor  = new MyDispatchActor();
        actor.start();

        actor.send(17);
        actor.send('Hello');

        // actor.join()
    }

    void testMyDefaultActor() {
        MyDefaultActor actor = new MyDefaultActor()
        actor.start()

        // blocks while waiting
        assert 'got message 17' == actor.sendAndWait(17)
        assert 'got message Hello' == actor.sendAndWait('Hello')
    }

    void testForkJoin() {
        File rootDir = new File("C:\\Users\\edush\\GItHub\\learning\\src")
        //feel free to experiment with the number of fork/join threads in the pool
        withPool(3) {pool ->
            println """Number of files: ${
                runForkJoin(rootDir) {file ->
                    long count = 0
                    file.eachFile {
                        if (it.isDirectory()) {
                            println "Forking a child task for $it"
                            forkOffChild(it)       //fork a child task
                        } else {
                            count++
                        }
                    }
                    return count + (childrenResults.sum(0))
                    //use results of children tasks to calculate and store own result
                }
            }"""
        }
    }

    class MyDispatchActor extends DynamicDispatchActor {
        def onMessage(int i) {
            println('got int ' + i)
            sleep(2000)
            reply('got int ' + i)
        }

        def onMessage(String s) {
            println('got string: ' + s)
            sleep(2000)
            reply('got string ' + s)
        }
    }

    class MyDefaultActor extends DefaultActor {

        void act() {
            println("Started MyDefaultActor")
            // do something on startup
            loop {
                react { def msg ->
                    println "got message: $msg"
                    reply('got message ' + msg)
                }
            }
        }
/*
        def onMessage(int i){
            println('got int ' + i)
        }
        def onMessage(String s){
            println('got string: ' + s)
        }
*/
    }
}