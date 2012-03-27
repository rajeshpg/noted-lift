package com.noted {
  package snippet {

    import scala.xml.NodeSeq
    import scala.xml.Unparsed
    import net.liftweb.util.Helpers
    import scala.xml.Text
    import net.liftweb.http.SHtml
    import Helpers._
    import net.liftweb.http.RequestVar
    import com.noted.model._
    import net.liftweb.http.S
    import net.liftweb.common._
    import net.liftweb.http.js.JsCmd
    import com.noted.comet.NotesServer

    object Notes {

      def noteForm(in: NodeSeq): NodeSeq = {
        bind("f", in,
          "desc" -%> SHtml.text(note.description.is, note.description(_)),
          "submit" -%> SHtml.submit("Note It !", add)) ++ SHtml.hidden(add _)
      }

      def delete(note: Note): JsCmd = {
        println("delete")
        note.delete_!
        NotesServer ! "refresh"
        S.error("descError", "note deleted")
      }

      def add() = {
        if (note.description.isEmpty()) {
          S.error("descError", "Please enter a note")
        } else {
          note.save
          NotesServer ! "refresh"
        }

      }

    }

    object note extends RequestVar[Note]({ Note.create })

  }
}

